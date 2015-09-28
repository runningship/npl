package org.bc.npl.concept;

import java.util.ArrayList;
import java.util.List;


public interface Expect {
	default List<WordHandler<?>> getExpectings(){
		List<WordHandler<?>> list =new ArrayList<WordHandler<?>>();
		return list;
	}
}
