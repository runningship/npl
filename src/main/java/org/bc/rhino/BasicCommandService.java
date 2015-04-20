package org.bc.rhino;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bc.npl.entity.Action;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;
import org.bc.web.ModelAndView;
import org.bc.web.Module;
import org.bc.web.WebMethod;

@Module(name="/base/cmd")
public class BasicCommandService {

	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	static RhinoService rhinoService = new RhinoService();
	public static final String ActionNamePattern = "(.)*@(.)+@(.)*";
	@WebMethod
	public ModelAndView add(String conts){
		ModelAndView mv = new ModelAndView();
		Matcher match = Pattern.compile(ActionNamePattern).matcher(conts);
		if(match.matches()){
			String name = match.group();
			String realName = name.replace("@", "");
			Action po = dao.getUniqueByKeyValue(Action.class, "name", realName);
			if(po==null){
				po = new Action();
				po.name = realName;
			}
			po.conts = conts.replace(name, realName); 
			dao.saveOrUpdate(po);
			mv.data.put("result", "success");
		}else{
			Object result = rhinoService.exec(conts);
			mv.data.put("result", result.toString());
		}
		return mv;
	}
}
