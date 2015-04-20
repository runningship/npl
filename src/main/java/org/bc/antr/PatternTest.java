package org.bc.antr;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class PatternTest {
	static Map<String,String> rules = null;
	public static void main(String[] args) throws IOException, URISyntaxException{
		rules = getRules("rule.txt");
		Pattern.compile("[[一二三四五六七八九十]{1}[[十百千万亿]{1}]*]+").matcher("一").appendTail(new StringBuffer());
//		match("数字" , "一百二十三");
		System.out.println();
	}
	
	public static void match(String ruleName , String text){
		
	}
	public static Map<String,String> getRules(String fileName) throws IOException, URISyntaxException{
		List<String> lines = FileUtils.readLines(new File(PatternTest.class.getResource(fileName).toURI()));
		Map<String,String> rules = new HashMap<String , String>();
		
		for(String line : lines){
			String[] arr = line.split(":");
			rules.put(arr[0], arr[1]);
		}
		return rules;
	}
}
