package org.bc.rhino;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bc.npl.entity.NplFunction;
import org.bc.npl.entity.NplRule;
import org.bc.npl.entity.Token;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;
import org.bc.web.ModelAndView;
import org.bc.web.Module;
import org.bc.web.WebMethod;


@Module(name="/test")
public class RuleEngine {

	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	private static final String Match_Str = "match_str";
	static RhinoService rhinoService = new RhinoService();
	List<Token> tokens = null;
	List<NplRule> rules = null;
	List<NplFunction> funcs = null;
	public RuleEngine(){
		tokens = dao.listByParams(Token.class	, "from Token");
		rules = dao.listByParams(NplRule.class, "from NplRule");
		// replace token into rules
		for(Token token : tokens){
			for(NplRule rule : rules){
				rule.name = rule.name.replace(token.name, token.conts);
			}
		}
		funcs = dao.listByParams(NplFunction.class, "from NplFunction");
		for(NplFunction func : funcs){
			rhinoService.exec(func.conts);
		}
	}
	@SuppressWarnings("restriction")
	@WebMethod
	public ModelAndView exec(String conts){
		ModelAndView mv = new ModelAndView();
		Map<String, String> rule = getMatchRule(conts);
		if(rule==null){
			mv.data.put("result", conts);
			return mv;
		}
		rhinoService.bindings.put(Match_Str, rule.get(Match_Str));
		Object result = rhinoService.exec(rule.get("action"));
		if(conts.equals(result)){
			//
			System.out.println("死循环?");
			mv.data.put("result", conts);
			return mv;
		}
		
		return exec(String.valueOf(result));
	}
	
	private Map<String , String> getMatchRule(String conts){
		for(NplRule rule : this.rules){
			Matcher match = Pattern.compile(rule.name).matcher(conts);
			if(match.matches()){
				String str = match.group();
				Map<String, String> map = new HashMap<String, String>();
				map.put(Match_Str, str);
				map.put("action", rule.action);
				map.put("ruleName", rule.name);
				return map;
			}
		}
		return null;
	}
}
