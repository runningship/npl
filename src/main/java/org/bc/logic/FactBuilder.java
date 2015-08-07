package org.bc.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bc.npl.entity.Fact;

public class FactBuilder {

	public void build(List<String> words , List<Fact> context){
		if(words.isEmpty()){
			return;
		}
		Fact fact = new Fact();
		List<String> subList = new ArrayList<String>();
		for(int i=0;i<words.size();i++){
			String word = words.get(0);
			if("的".equals(word)){
				if(i==0){
					return;
				}
				if(i==words.size()-1){
					//以的子结尾
					fact.x = words.get(i-1);
					fact.f = LogicConstant.F0;
					fact.y=UUID.randomUUID().toString();
					break;
				}else{
					fact.x = words.get(i-1);
					fact.f = words.get(i+1);
					fact.y=UUID.randomUUID().toString();
					subList = words.subList(i+2, words.size());
					subList.add(0,fact.y);
					break;
				}
			}
		}
		context.add(fact);
		build(subList , context);
	}
}
