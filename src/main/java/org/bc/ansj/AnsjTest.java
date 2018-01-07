package org.bc.ansj;

import java.util.List;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

public class AnsjTest {

	public static void main(String[] args){
		String str = "小明的爸爸是大明";
		// var xx = new Object();
		// xx.tag = 小明
		// xx.爸爸=大明
		String str2 = "大明的爸爸是老明";
		// var yy = new Object();
		// yy.tag = 大明;
		// yy.爸爸=老明
		
		Result result = ToAnalysis.parse("榴莲都有一种奇怪的味道"); 
		List<Term> terms = result.getTerms();
		for(Term term : terms){
			System.out.println(term.getName());
		}
	}
}
