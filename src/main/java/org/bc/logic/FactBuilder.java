package org.bc.logic;

import java.util.List;

import org.bc.npl.entity.Fact;

public class FactBuilder {

	public void build(List<String> words){
		for(int i=0;i<words.size();i++){
			String word = words.get(0);
			if("的".equals(word)){
				if(i==0){
					throw new RuntimeException("的字位置错误");
				}
				if(i==words.size()-1){
					//以的子结尾
					Fact fact = new Fact();
					fact.x = words.get(i-1);
					fact.f = LogicConstant.F0;
				}
			}
		}
	}
}
