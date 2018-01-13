package org.bc.participle;

import java.util.List;

import org.junit.Test;

public class TokenManagerTest {

	@Test
	public void testParseTokenFromString(){
		TokenManager tm = new TokenManager();
		String str1= "电站于1966年6月施工";
		String str2 = "晚上吃kfc";
		String str3 = "认为大渡河在大金川以上有三源：梭磨河、绰斯甲河（上源为青海的杜柯河、多柯河）、足木足河";
		String str4 = "有我的名字叫yexinzhou";
		List<String> tokens = tm.parseTokenFromSentence(str1);
		PrintUtils.printList(tokens);
		PrintUtils.printSepLine();
		
		tokens = tm.parseTokenFromSentence(str2);
		PrintUtils.printList(tokens);
		PrintUtils.printSepLine();
		
		tokens = tm.parseTokenFromSentence(str3);
		PrintUtils.printList(tokens);
		PrintUtils.printSepLine();
		
		tokens = tm.parseTokenFromSentence(str4);
		PrintUtils.printList(tokens);
		PrintUtils.printSepLine();
	}
	
	@Test
	public void testGetNumberFromString(){
		String str1 = "电站于1966年6月施工";
		String str2 = "水库移民101830";
		String str3 = "降雨量上游600~700毫米,顶宽21.6公尺";
		String str4 = "xx3";
		String str5 = "2";
		String str6 = "2.3";
		List<String> nums = null;
		
		nums = TokenUtils.getNumbers(str1);
		PrintUtils.printList(nums);
		PrintUtils.printSepLine();
		
		nums = TokenUtils.getNumbers(str2);
		PrintUtils.printList(nums);
		PrintUtils.printSepLine();
		
		nums = TokenUtils.getNumbers(str3);
		PrintUtils.printList(nums);
		PrintUtils.printSepLine();
		
		nums = TokenUtils.getNumbers(str4);
		PrintUtils.printList(nums);
		PrintUtils.printSepLine();
		
		nums = TokenUtils.getNumbers(str5);
		PrintUtils.printList(nums);
		PrintUtils.printSepLine();
		
		nums = TokenUtils.getNumbers(str6);
		PrintUtils.printList(nums);
		
		
	}
}
