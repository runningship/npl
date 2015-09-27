package org.bc.npl.concept;

import java.util.HashMap;
import java.util.Map;

public interface 物体{
	public String 视觉();
	
	public String 嗅觉();
	
	public String 味觉();
	
	public String 听觉();
	
	public String 感觉();
	
	default Map<String , WordHandler> getExpectings(){
		Map<String , WordHandler> map = new HashMap<String , WordHandler>();
		map.put("方位", new WordHandler(){
			//
		});
		return map;
	}
}
