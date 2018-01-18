package org.bc.participle;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bc.npl.StartUpListener;
import org.bc.participle.entity.Article;
import org.bc.participle.entity.Line;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.Page;
import org.bc.sdak.TransactionalServiceHelper;
import org.junit.Test;

public class SentenceManagerTest {

	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	@Test
	public void testParseSentenceFromText() throws IOException{
		Article article = dao.get(Article.class, 95);
		SentenceManager sm = new SentenceManager();
		List<String> sentenceList = sm.parseSentenceFromText(article.text);
		PrintUtils.printlnList(sentenceList);
	}
	
	@Test
	public void testParseLinesFromText() throws IOException{
		StartUpListener.initDataSource();
		PplManager pm = new PplManager();
		//ThreadSessionHelper.setDbType(ThreadSessionHelper.Sql_Server_Db);
		Article article = dao.get(Article.class, 95);
		List<Line> lines = pm.parseLinesFromText(article);
		PrintUtils.printlnList(lines);
	}
	
	@Test
	public void testTrainSingleArticle() throws IOException{
		StartUpListener.initDataSource();
		PplManager pm = new PplManager();
		Article article = dao.get(Article.class, 95);
		pm.parseLinesAndSave(article);
	}
	
	@Test
	public void testTrainByArticles(){
		StartUpListener.initDataSource();
		trainArticlesByPage();
	}
	
	private int trainArticlesByPage(){
		Page<Article> page = new Page<Article>();
//		page = dao.findPage(page, "from Article where trainAccount is null or trainAccount=?",0);
		page = dao.findPage(page, "from Article order by trainAccount asc, interest desc");
		PplManager pm = new PplManager();
		int savedCount = 0;
		for(Article article : page.result){
			savedCount = pm.parseLinesAndSave(article);
		}
		return savedCount;
	}
	
	@Test
	public void testTrainAllArticles(){
		StartUpListener.initDataSource();
		int count = 0;
		do{
			count = trainArticlesByPage();
		}while(count>0);
	}
}
