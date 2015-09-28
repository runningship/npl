package org.bc.npl.concept;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args){
		String str = "天上";
		List<Object> list = new ArrayList<Object>();
//		list.add(new 天());
//		list.add(new 上());
		
		process(new 天() , new 上());
	}

	private static void process(Expect obj1, Expect obj2) {
		for(WordHandler handler : obj1.getExpectings()){
			if(obj2.getClass().isAssignableFrom(handler.getTargetClass())){
				handler.process(obj2);
			}
		}
	}
}
