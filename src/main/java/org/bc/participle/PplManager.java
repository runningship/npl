package org.bc.participle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.bc.participle.entity.Article;
import org.bc.participle.entity.Line;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;

/**
 * 以文章以单元训练
 * @author yexinzhou
 *
 */
public class PplManager {

	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	private SentenceManager sm = new SentenceManager();
	
	private TokenManager tm = new TokenManager();
	
	private LineManager lm = new LineManager();
	
	public List<Line> parseLinesFromText(Article article){
		List<Line> lines = new ArrayList<Line>();
		if(article==null || StringUtils.isEmpty(article.text)){
			return lines;
		}
		
		List<String> sentenceList = sm.parseSentenceFromText(article.text);
		for(String sentence : sentenceList){
			List<String> tokens = tm.parseTokenFromSentence(sentence);
			LineManager lm = new LineManager();
			lines.addAll(lm.parseLinesFromTokens(tokens));
		}
		return lines;
	}
	/**
	 * 返回产生新词数量
	 * @param article
	 * @return
	 */
	public int parseLinesAndSave(Article article){
		System.out.println("开始训练:"+article.title);
		if(StringUtils.isEmpty(article.text)){
			dao.delete(article);
			return 0;
		}
		List<Line> lines = parseLinesFromText(article);
		List<Line> reducedLines = reduceLines(lines);
		int batch = reducedLines.size()/100;
		if(batch==0){
			batch=1;
		}
		int savedCount = 0;
		for(int i=0;i<reducedLines.size();i++){
			Line line = reducedLines.get(i);
			line.articleId = article.id;
			int saved = lm.saveLine(line);
//			System.out.println(i);
			if(saved==1){
				savedCount++;
			}
//			if(i%batch==0){
//				System.out.println("处理完成"+i+"个词(共"+reducedLines.size()+")");
//			}
		}
		if(article.trainAccount==null){
			article.trainAccount = 1;
		}else{
			article.trainAccount++;
		}
		article.lastTrainTime = new Date();
		// 修改兴趣度
		if(savedCount>0){
			article.interest-= savedCount;
			dao.saveOrUpdate(article);
		}else{
			System.out.println("无产出词:"+article.title);
		}
		System.out.println("完成训练:"+article.title+",产出词:"+savedCount+"个");
		return savedCount;
	}
	
	/**
	 * 归并，把多个相同的归并成一个，只是score加起来
	 * @return 
	 */
	private List<Line> reduceLines(List<Line> lines){
		Map<String , Line> map = new HashMap<String , Line>();
		for(int i=0;i<lines.size();i++){
			Line line = lines.get(i);
			if(map.containsKey(line.name)){
				Line tmp = map.get(line.name);
				tmp.score++;
			}else{
				line.score=1;
				line.index =i;
				map.put(line.name, line);
			}
		}
		List<Line> tmp = new ArrayList<Line>();
		tmp.addAll(map.values());
		List<Line> result = new ArrayList<Line>();
		Collections.sort(tmp, new Comparator<Line>() {

			@Override
			public int compare(Line o1, Line o2) {
				if(o1.score>o2.score){
					return -1;
				}else if(o1.score == o2.score){
					//根据前后顺序排序
					if(o1.index>o2.index){
						return 1;
					}else if(o1.index==o2.index){
						return 0;
					}else{
						return -1;
					}
				}else{
					return 1;
				}
			}
		});
		if(tmp.isEmpty()){
			return result;
		}
		// 只取前1个
		result.add(tmp.get(0));
//		for(int i=0;i<tmp.size();i++){
//			if(i>=10){
//				break;
//			}
//			result.add(tmp.get(i));
//		}
		return result;
	}
}
