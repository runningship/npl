package org.bc.participle;

import java.io.IOException;
import java.util.List;

import org.bc.npl.StartUpListener;
import org.bc.participle.entity.Article;
import org.bc.participle.entity.Line;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.Page;
import org.bc.sdak.TransactionalServiceHelper;
import org.junit.Before;
import org.junit.Test;

public class SentenceManagerTest {

	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	@Before
	public void init(){
		StartUpListener.initDataSource();
		GuessedWordCache.loadCache();
	}
	@Test
	public void testParseSentenceFromText() throws IOException{
		Article article = dao.get(Article.class, 95);
		SentenceManager sm = new SentenceManager();
		List<String> sentenceList = sm.parseSentenceFromText(article.text);
		PrintUtils.printlnList(sentenceList);
	}
	
	@Test
	public void testParseLinesFromText() throws IOException{
		PplManager pm = new PplManager();
		//ThreadSessionHelper.setDbType(ThreadSessionHelper.Sql_Server_Db);
		Article article = dao.get(Article.class, 2284);
		List<Line> lines = pm.parseLinesFromText(article);
		PrintUtils.printlnList(lines);
	}
	
	@Test
	public void testTrainSingleArticle() throws IOException, DuplicateTrainingException{
		PplManager pm = new PplManager();
		Article article = dao.get(Article.class, 2284);
		pm.parseLinesAndSave(article);
	}
	
	@Test
	public void testTrainArticles() throws OverTrainedException{
		trainArticlesByPage();
	}
	
	private int trainArticlesByPage() throws OverTrainedException{
		Page<Article> page = new Page<Article>();
		GuessWordManager gwm = new GuessWordManager();
//		page = dao.findPage(page, "from Article where trainAccount is null or trainAccount=?",0);
		page = dao.findPage(page, "from Article where trainOver=0 order by trainAccount asc, interest desc");
		PplManager pm = new PplManager();
		int savedCount = 0;
		if(page.result.isEmpty()){
			throw new OverTrainedException();
		}
		for(Article article : page.result){
			try {
				savedCount = pm.parseLinesAndSave(article);
			} catch (DuplicateTrainingException e) {
				gwm.doGuess();
				GuessedWordCache.refresh();
			}
		}
		return savedCount;
	}
	
	@Test
	public void testTrainAllArticles(){
		StartUpListener.initDataSource();
		while(true){
			try {
				trainArticlesByPage();
			} catch (OverTrainedException e) {
				return;
			}
		}
	}
}
