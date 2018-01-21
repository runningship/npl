package org.bc.participle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bc.participle.entity.GuessedWord;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;

/**
 * GuessedWord缓存
 * @author yexinzhou
 *
 */
public class GuessedWordCache {

	private static Map<String , GuessedWord> cache = new HashMap<String , GuessedWord>();
	

	public static void loadCache(){
		CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
		List<GuessedWord> list = dao.listByParams(GuessedWord.class, "from GuessedWord");
		for(GuessedWord word : list){
			cache.put(word.name, word);
		}
	}
	
	public static boolean isExsistInGuessedWord(String leftToken , String rightToken) {
		return cache.containsKey(leftToken+rightToken);
	}
	
	public static boolean isExsistInGuessedWord(String text) {
		return cache.containsKey(text);
	}
	
	public static GuessedWord getGuessedWord(String text){
		return cache.get(text);
	}
	
	public synchronized static void refresh(){
		cache.clear();
		loadCache();
	}
}
