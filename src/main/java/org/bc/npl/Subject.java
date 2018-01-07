package org.bc.npl;

import java.util.ArrayList;
import java.util.List;

public class Subject {

	public String name;
	
	public List<Subject> you = new ArrayList<Subject>();
	
	//subject是一个集合概念，这个是集合元素
	public List<Subject> instances = new ArrayList<Subject>();
	
	//集合值的属性
	public List<Subject> de = new ArrayList<Subject>();
	
	//集合层次中所属的类别
	public List<Subject> types = new ArrayList<Subject>();
	
	public Subject deParent;
	
	public Subject youParent;
	
}
