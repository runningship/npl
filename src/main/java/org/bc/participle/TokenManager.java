package org.bc.participle;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 将句子分成独立的词：单个汉字和非汉字字符串(数字，英文字母或单词，其他符号)
 *
 */
public class TokenManager {

	private static List<Character> stopTokens = new ArrayList<Character>();
	
	public static final char Seperate_Char = ' ';
	
	static{
		stopTokens.add(' ');
		stopTokens.add('（');
		stopTokens.add('）');
		stopTokens.add('、');
		stopTokens.add('：');
		stopTokens.add('【');
		stopTokens.add('】');
		stopTokens.add('-');
		stopTokens.add('"');
		stopTokens.add('《');
		stopTokens.add('》');
			
		stopTokens.add('(');
		stopTokens.add(')');
		stopTokens.add(':');
		stopTokens.add('[');
		stopTokens.add(']');
		stopTokens.add('-');
		stopTokens.add('ˉ');
		stopTokens.add(' ');
		stopTokens.add('“');
		stopTokens.add('”');
	}
	
	public List<String> parseTokenFromSentence(String text){
		List<String> tokens = new ArrayList<String>();
		if(text==null){
			return tokens;
		}
		for(int i=0;i<text.length();i++){
			char ch = text.charAt(i);
			if(ch>='0' && ch<='9'){
				String number = TokenUtils.guessNumber(text,i);
				tokens.add(number);
				i+=number.length()-1;
			}else if(stopTokens.contains(ch)){
				tokens.add(String.valueOf(Seperate_Char));
			}else if((ch>='a' && ch<='z') || (ch>='A' && ch<='Z')){
				String word = guessWord(text , i);
				tokens.add(word);
				i+=word.length()-1;
			}else{
				tokens.add(String.valueOf(ch));
			}
		}
		return tokens;
	}
	
	private String guessWord(String text ,int start){
		StringBuilder sb = new StringBuilder();
		sb.append(text.charAt(start));
		for(int i=start+1;i<text.length();i++){
			char ch = text.charAt(i);
			if((ch>='a' && ch<='z') || ch=='-' || (ch>='A' && ch<='Z')){
				sb.append(ch);
				continue;
			}else{
				break;
			}
		}
		return sb.toString();
	}
}
