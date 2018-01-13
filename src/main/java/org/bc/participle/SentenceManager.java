package org.bc.participle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 将文章分解成句子，上下文无关
 * @author xzye
 *
 */
public class SentenceManager {

	public static List<Character> stopTokens = new ArrayList<Character>();
	
	static{
		stopTokens.add(',');
		stopTokens.add(';');
		//stopTokens.add('.');
		stopTokens.add('?');
		stopTokens.add('!');
		stopTokens.add(')');
		stopTokens.add('(');
		
		stopTokens.add('，');
		stopTokens.add('。');
		stopTokens.add('；');
		stopTokens.add('！');
		stopTokens.add('？');
//		stopTokens.add('）');
//		stopTokens.add('（');
		
		stopTokens.add('\n');
		stopTokens.add('\r');
	}
	
	public List<String> parseSentenceFromText(String text){
		List<String> result = new ArrayList<String>();
		if(text==null){
			return result;
		}
		int start = 0;
		for(int i=0;i<text.length();i++){
			if(stopTokens.contains(text.charAt(i))){
				String str =text.substring(start, i); 
				i++;
				start = i;
				str = str.trim();
				if(!StringUtils.isBlank(str)){
					result.add(str);
				}
			}
		}
		return result;
	}
}
