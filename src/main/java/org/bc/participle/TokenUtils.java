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
}
