package org.bc.rhino;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bc.sdak.SimpDaoTool;

public class RhinoTest {

	private static ScriptEngineManager sm = new ScriptEngineManager();
	
	public static void main(String[] args){
		ScriptEngine engine = sm.getEngineByName("JavaScript");
		Bindings bindings = prepareBindings(engine);
		engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		String script ="1+1;";
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
	}
	
	private static Bindings prepareBindings(ScriptEngine engine) {
		Bindings bindings = engine.createBindings();
		bindings.put("dao", SimpDaoTool.getGlobalCommonDaoService());
		bindings.put("engine", engine);
		return bindings;
	}
}
