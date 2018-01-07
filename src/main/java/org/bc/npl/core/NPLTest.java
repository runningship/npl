package org.bc.npl.core;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.bc.npl.StartUpListener;

public class NPLTest {

	public static void main(String[] args){
		StartUpListener.initDataSource();
//		String str = "小米手机被黄牛抬价了很多";
		String str = "小明的爸爸是大明";
//		String str = "三是数量";
		Lexer p = new Lexer();
		List<String> lexerResults = p.exec(str);
		for(String r : lexerResults){
			System.out.println(r);
		}
		//暂取词法分析结果的第一个
		if(lexerResults.isEmpty()){
			return;
		}
		Parser parser = new Parser();
		String[] temp = lexerResults.get(0).split(Lexer.ExprSeprator);
		List<String> words = new ArrayList<String>();
		for(int i=0;i<temp.length;i++){
			if(StringUtils.isNotEmpty(temp[i])){
				words.add(temp[i]);
			}
		}
//		List<OperMatch> opers = parser.matchOper(words.toArray(new String[]{}));
		Block block = parser.buildBlock(words);
		System.out.println(block.toString());
		Translator trans = new Translator();
		JSONObject jobj = trans.visit(block);
		System.out.println(jobj);
		//执行或者理解语义,做出相依的反应
		//如被记忆,引起某些情感反应，如果是询问可以做出回答等
		Caculator caculator = new Caculator();
		caculator.run(jobj);
	}
}
