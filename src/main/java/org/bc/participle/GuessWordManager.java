package org.bc.participle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.bc.participle.entity.GuessedWord;
import org.bc.participle.entity.Line;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;

/**
 * 从Line中过滤可能的词
 * @author yexinzhou
 *
 */
public class GuessWordManager {

	private CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	public static final int Guessed_Word_Score_Threshold = 10;
	
	public int doGuess(){
		List<Line> list = dao.listByParams(Line.class, "from Line where score>"+Guessed_Word_Score_Threshold);
		//先去重
		Map<String , Line> reduceMap = new HashMap<String,Line>();
		for(Line line : list){
			if(reduceMap.containsKey(line.name)){
				reduceMap.get(line.name).score++;
			}else{
				reduceMap.put(line.name, line);
			}
		}
		int successCount = 0;
		for(Line line : reduceMap.values()){
			System.out.println("处理数据: "+line.name);
			if(moveWord(line)){
				successCount++;
			}
		}
		// clear rest lines
		dao.execute("delete from Line");
		System.out.println("新增GuessedWord"+successCount+"个");
		return successCount;
	}

	@Transactional
	private boolean moveWord(Line line) {
		boolean result = false;
		if(line!=null){
			if(TokenUtils.isChinese(line.name)){
				GuessedWord word = new GuessedWord();
				word.leftToken = line.leftToken;
				word.rightToken = line.rightToken;
				word.name = line.name;
				word.score = 1;
				addWordWithoutCheck(word);
				result= true;
			}else{
				System.out.println(line.name+"包含非中文字符");
			}
//			removeLine(line);
		}
		return result;
	}
	
	public GuessedWord getWordFromTokens(List<String> tokens, int start){
		if(start>=tokens.size()){
			return null;
		}
		GuessedWord word = null;
		for(int i=start+2;i<=tokens.size();i++){
			List<String> tokenSet = tokens.subList(start, i);
			String wordText = StringUtils.join(tokenSet, "");
			if(GuessedWordCache.isExsistInGuessedWord(wordText)){
				word = GuessedWordCache.getGuessedWord(wordText);
			}else{
				break;
			}
		}
		return word;
	}

	private void addWordWithoutCheck(GuessedWord word) {
		dao.saveOrUpdate(word);
	}
	private void addWord(GuessedWord word) {
		GuessedWord po = dao.getUniqueByKeyValue(GuessedWord.class, "name", word.name);
		if(po==null){
			dao.saveOrUpdate(word);
		}else{
			System.out.println(word.name+" 已存在，更新score");
			po.score++;
			dao.saveOrUpdate(po);
		}
	}
}
