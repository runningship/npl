package org.bc.npl.core;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.bc.npl.entity.Aggregation;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.SimpDaoTool;
import org.bc.sdak.TransactionalServiceHelper;

//遍历词法树，一边遍历，一边"理解",加入集合层次作用，处理成初级的语义结构,
//将表达式数这种接近自然语言的数据结构翻译成高级程序语言数据结构
public class Translator {

	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	/**
	 * 返回一个语义结构,这个过程中运算符号参与作用，语义被量化到各个维度上。
	 */
	public JSONObject visit(Block block){
		if(block.isOper){
			return visitOper(block);
		}else{
			return visitLeaf(block);
		}
	}

	private JSONObject visitLeaf(Block block) {
		JSONObject jobj = new JSONObject();
		for(int i=0;i<block.text.size();i++){
			String str = block.text.get(i);
			//find something about str
//			jobj.put("text", str);
			String aggr = BasicConcept.findRootAggregation(str);
			if(aggr==null){
				aggr = BasicConcept.findSet(str);
				if(aggr!=null){
					jobj.put("set", aggr);
				}else{
					jobj.put(i+"", str);
				}
			}else{
				//set elem
				jobj.put(aggr, str);
			}
			
		}
		return jobj;
	}

	private JSONObject visitOper(Block block) {
		if("的".equals(block.text.get(0))){
			return visitOf(block);
		}else if("有".equals(block.text.get(0))){
			return visitHas(block);
		}else if("是".equals(block.text.get(0))){
			return visitIs(block);
		}else{
			return null;
		}
	}

	private JSONObject visitIs(Block block) {
		JSONObject left = visit(block.left);
		JSONObject right = visit(block.right);
		right.put("left", left);
		right.put("oper", "是");
		return right;
	}

	private JSONObject visitOf(Block block) {
		JSONObject left = visit(block.left);
		JSONObject right = visit(block.right);
		if(left==null){
			System.out.println("的字前应该要有内容");
			return null;
		}
		if(right==null){
			System.out.println("的字后面没有内容改怎么处理呢?");
		}
		for(Object key : left.keySet()){
			//找到对应的条件类别，设置到right对象上
			if("指代".equals(key)){
				right.put("whos", left.get(key));
			}else{
				right.put(key, left.get(key));
			}
		}
		//小明的爸爸
		//一部新的手机
		return right;
	}
	
	private JSONObject visitHas(Block block){
		JSONObject left = visit(block.left);
		JSONObject right = visit(block.right);
		if(left==null){
			System.out.println("有字前应该要有内容");
			return null;
		}
		if(right==null){
			System.out.println("有字后应该要有内容");
			return null;
		}
		if(!left.has("有")){
			left.put("有", new ArrayList());
		}
		List hasList =(List) left.get("有");
		hasList.add(right);
		return left;
	}
}
