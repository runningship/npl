package org.bc.npl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.bc.npl.core.Block;
import org.bc.npl.core.Lexer;
import org.bc.npl.core.Parser;
import org.bc.npl.util.DataHelper;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;
import org.bc.web.ModelAndView;
import org.bc.web.Module;
import org.bc.web.WebMethod;

@Module(name="/")
public class NplService {

	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	@WebMethod
	public ModelAndView tokenTree(String text){
		ModelAndView mv = new ModelAndView();
//		String str = "小明有两朵小红花";
		Lexer p = new Lexer();
		List<String> lexerResults = p.exec(text);
		for(String r : lexerResults){
			System.out.println(r);
		}
		//暂取词法分析结果的第一个
		if(lexerResults.isEmpty()){
			return mv;
		}
		Parser parser = new Parser();
		String[] temp = lexerResults.get(0).split(Lexer.ExprSeprator);
		List<String> words = new ArrayList<String>();
		for(int i=0;i<temp.length;i++){
			if(StringUtils.isNotEmpty(temp[i])){
				words.add(temp[i]);
			}
		}
		Block block = parser.buildBlock(words);
		JSONObject root = DataHelper.toDrawableTree(block);
		mv.jspData.put("tree", root.toString());
		return mv;
	}
}
