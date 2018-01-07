package org.bc.npl.core;



//词法块,一个语义块可以是一个句子，也可以是一个短语
//理论上一个句子最终转换成一个词法块，歧义(双关)句子可以转换成多个词法块
//分词树,相当于表达式树
public class Block {
	
	public String text;
	
	public Block right;
	
	public Block left;
	
	public boolean isOper;
}
