package org.bc.participle;

import java.util.ArrayList;
import java.util.List;

public class TokenUtils {

	public static List<String> getNumbers(String text){
		List<String> nums = new ArrayList<String>();
		if(text==null){
			return nums;
		}
		for(int i=0;i<text.length();i++){
			char ch = text.charAt(i);
			if(ch>='0' && ch<='9'){
				String number = TokenUtils.guessNumber(text,i);
				nums.add(number);
				i+=number.length()-1;
			}
		}
		return nums;
	}
	
	public static String guessNumber(String text, int start) {
		StringBuilder sb = new StringBuilder();
		sb.append(text.charAt(start));
		for(int i=start+1;i<text.length();i++){
			char ch = text.charAt(i);
			if((ch>='0' && ch<='9') || ch=='.'){
				sb.append(ch);
				continue;
			}else{
				break;
			}
		}
		return sb.toString();
	}
	
    private static boolean isChineseByScript(char c) {  
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);  
        if (sc == Character.UnicodeScript.HAN) {  
            return true;  
        }  
        return false;  
    }  
    
    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!isChineseByScript(c)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isChinesePuctuation(char c) {  
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS  
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS  
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {//jdk1.7  
            return true;  
        }  
        return false;  
    }
}
