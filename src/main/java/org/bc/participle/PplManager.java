package org.bc.participle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.bc.participle.entity.Article;
import org.bc.participle.entity.Line;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;

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
	
	public void parseLinesAndSave(Article article){
		System.out.println("开始训练:"+article.text.substring(0, 10));
		List<Line> lines = parseLinesFromText(article);
		List<Line> reducedLines = reduceLines(lines);
		int batch = reducedLines.size()/100;
		for(int i=0;i<reducedLines.size();i++){
			Line line = reducedLines.get(i);
			line.articleId = article.id;
			lm.saveLine(line);
			if(i%batch==0){
				System.out.println("处理完成"+i+"个词(共"+reducedLines.size()+")");
			}
		}
		if(article.trainAccount==null){
			article.trainAccount = 1;
		}else{
			article.trainAccount++;
		}
		dao.saveOrUpdate(article);
	}
	
	/**
	 * 归并，把多个相同的归并成一个，只是score加起来
	 * @return 
	 */
	private List<Line> reduceLines(List<Line> lines){
		Map<String , Line> map = new HashMap<String , Line>();
		for(Line line : lines){
			if(map.containsKey(line.name)){
				map.get(line.name).score++;
			}else{
				map.put(line.name, line);
			}
		}
		List<Line> result = new ArrayList<Line>();
		result.addAll(map.values());
		return result;
	}
}
