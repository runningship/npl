package org.bc.npl.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bc.npl.entity.Oper;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;

//生成词法树AST
public class Parser {
	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	public Block buildBlock(List<String> words){
		int index = getNextOper(words);
		Block block = new Block();
		if(index<=-1){
			if(words.size()<=1){
				block.text = words.get(0);
				return block;
			}else{
				List<String> tmp = new ArrayList<String>();
				for(String w : words){
					tmp.add(w);
					tmp.add("的");
				}
				//删除最后一个的
				tmp.remove(tmp.size()-1);
				words = tmp;
				return buildBlock(words);
			}
		}
		 block.text = words.get(index);
		 block.isOper = true;
		 if(index>0){
			 //递归构建左子树
			 block.left = buildBlock(words.subList(0, index));
		 }
		 if(index<words.size()-1){
			 //递归构建右子树
			 block.right = buildBlock(words.subList(index+1, words.size()));
		 }
		 return block;
	}
	
	//找到句子中优先级最低的操作符,优先级最低的操作符最后运算，在深度优先树中，越处于上层
	private int getNextOper(List<String> words){
		int pos = -1;
		int priority = Integer.MAX_VALUE;
		for(int i=0;i<words.size();i++){
			Oper oper = dao.getUniqueByKeyValue(Oper.class, "text", words.get(i));
			//操作符可能是一个集合
			List<Map> list2 = dao.listAsMap("select op.priority as priority from Oper op , Aggregation aggr where op.text=aggr.sets and elem=? " , words.get(i));
			if(oper!=null){
				if(oper.priority<=priority){
					priority = oper.priority;
					pos = i;
					break;
				}
				
			}else{
				if(!list2.isEmpty()){
					int pri = (int) list2.get(0).get("priority");
					if(pri<=priority){
						priority = pri;
						pos = i;
						break;
					}
				}
				
			}
		}
		return pos;
	}
}
