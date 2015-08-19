package org.bc.npl;

import java.util.ArrayList;
import java.util.List;

public class Question {

	private static List<String> words = new ArrayList<String>();
	static{
		words.add("多少");
		words.add("几");
		words.add("吗");
		words.add("什么");
		words.add("怎么");
		words.add("哪");
		words.add("哪里");
	}
	
	public static boolean isQuestionWord(String word){
		return words.contains(word);
	}
	
	public static List<Subject> toQuery(Subject sbj){
		List<Subject> result = new ArrayList<Subject>();
		result.add(0,sbj);
		if(sbj.deParent!=null){
			Subject s = new Subject();
			s.name = "的";
			result.add(0,s);
			result.addAll(0, toQuery(sbj.deParent));
			return result;
		}
		if(sbj.youParent!=null){
			Subject s = new Subject();
			s.name = "有";
			result.add(0,s);
			result.addAll(0, toQuery(sbj.youParent));
			return result;
		}
		return result;
	}
}
