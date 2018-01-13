package org.bc.participle;

import java.util.List;

public class PrintUtils {

	public static void printList(List<?> list){
		for(Object obj : list){
			System.out.print(obj+",");
		}
		System.out.println();
	}
	
	public static void printlnList(List<?> list){
		for(Object obj : list){
			System.out.println(obj);
		}
	}
	
	public static void printSepLine(){
		System.out.println("----------------------------------------------------------------------");
	}
}
