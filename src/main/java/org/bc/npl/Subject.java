package org.bc.npl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class Subject {

	public String name;
	
	public List<JSONObject> attrs = new ArrayList<JSONObject>();
	
	public List<Refer> refers = new ArrayList<Refer>();
}
