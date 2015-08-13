package org.bc.npl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.bc.npl.core.Block;
import org.bc.npl.core.Lexer;
import org.bc.npl.core.Parser;
import org.bc.npl.entity.Fact;
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
		List<Fact> context = new ArrayList<Fact>();
		buildFact(block ,context);
		return mv;
	}
	
	private String buildFact(Block block , List<Fact> context){
		if(block.left==null && block.right==null){
			return block.text;
		}
		if("的".equals(block.text)){
			Fact fact = new Fact();
			context.add(fact);
			fact.x = buildFact(block.left , context);
			fact.f = buildFact(block.right , context);
			fact.y = UUID.randomUUID().toString();
			return fact.y;
		}else if("是".equals(block.text)){
			Fact fact = new Fact();
			context.add(fact);
			fact.x = buildFact(block.left , context);
			fact.f = buildFact(block.right , context);
			fact.y = "是";
			return "";
		}else{
			throw new RuntimeException("未处理的oper");
		}
	}
}
