package org.bc.rhino;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bc.sdak.SimpDaoTool;

public class RhinoService {

	private ScriptEngineManager sm = new ScriptEngineManager();
	private ScriptEngine engine = sm.getEngineByName("JavaScript");
	
	public RhinoService(){
		prepareBindings();
	}
	private  Bindings prepareBindings() {
		
		Bindings bindings = engine.createBindings();
		bindings.put("dao", SimpDaoTool.getGlobalCommonDaoService());
		bindings.put("engine", engine);
		engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		return bindings;
	}
	
	public Object exec(String script){
		Object result="";
		try {
			// 执行脚本
			if(script.contains("java.io")){
				throw new RuntimeException("java.io is forbiden,user sys.io instead");
			}
			result = engine.eval(script);
		}catch (ScriptException e) {
			e.printStackTrace();
		}
		System.out.println("result of action is : "+result);
		return result;
	}
}
