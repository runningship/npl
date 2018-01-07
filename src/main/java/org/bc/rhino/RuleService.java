package org.bc.rhino;

import org.bc.npl.entity.NplFunction;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;
import org.bc.web.ModelAndView;
import org.bc.web.Module;
import org.bc.web.WebMethod;

@Module(name="/rule")
public class RuleService {

	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	@WebMethod
	public ModelAndView add(String name,String action){
		ModelAndView mv = new ModelAndView();
		NplFunction po = dao.getUniqueByKeyValue(NplFunction.class, "name", name);
		if(po==null){
			po = new NplFunction();
			po.name = name;
		}
		po.conts = action.replace(name, name); 
		dao.saveOrUpdate(po);
		mv.data.put("result", "success");
		return mv;
	}
}
