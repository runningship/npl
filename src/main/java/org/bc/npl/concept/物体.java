package org.bc.npl.concept;

import java.util.ArrayList;
import java.util.List;

public interface 物体 extends Expect{
	default String 视觉(){
		return "";
	};
	
	default String 嗅觉(){
		return "";
	};
	
	default String 味觉(){
		return "";
	};
	
	default String 听觉(){
		return "";
	};
	
	default String 感觉(){
		return "";
	};
	
	default List<WordHandler<?>> getExpectings(){
		List<WordHandler<?>> list =new ArrayList<WordHandler<?>>();
		list.add(new WordHandler<位置>(){

			@Override
			public Object process(位置 obj2) {
				return null;
			}
			
		});
		return list;
	}
	
	
}
