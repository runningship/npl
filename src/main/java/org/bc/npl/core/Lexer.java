package org.bc.npl.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bc.npl.StartUpListener;
import org.bc.npl.entity.Word;
import org.bc.sdak.SimpDaoTool;

//分词器
public class Lexer {

	public static final String ExprSeprator = "-";
	public static void main(String[] args){
		StartUpListener.initDataSource();
		String str = "我有一部新手机";
		Lexer p = new Lexer();
		List<Expr> list = p.nextExpr(str);
		List<String> results = p.getResult(list);
		for(String r : results){
			System.out.println(r);
		}
	}
	
	
	/**
	 * 返回所有的分词可能性
	 */
	private List<String> getResult(List<Expr> exprs){
		List<String> results = new ArrayList<String>();
		if(exprs == null){
			return results;
		}
		for(Expr expr : exprs){
			if(expr.next.isEmpty()){
				results.add(expr.text);
			}else{
				for(String str : getResult(expr.next)){
					results.add(expr.text+ExprSeprator+str);
				}
			}
		}
		return results;
	}

	public List<String> exec(String str){
		List<Expr> exprs = nextExpr(str);
		return getResult(exprs);
	}

	private List<Word> getWordStartWith(String head) {
		return SimpDaoTool.getGlobalCommonDaoService().listByParams(Word.class, "from Word where text like ? order by len(text) desc", 
				new Object[]{head+"%"});
	}
	
	private List<Expr> nextExpr(String str){
		List<Expr> exprs = new ArrayList<Expr>();
		if(StringUtils.isEmpty(str)){
			return exprs;
		}
		
		List<Word> list = getWordStartWith(String.valueOf(str.charAt(0)));
		for(Word w : list){
			if(str.startsWith(w.text)){
				Expr expr = new Expr();
				expr.text = w.text;
				exprs.add(expr);
				break;
			}
		}
		if(exprs.isEmpty()){
			Expr expr = new Expr();
			expr.text = String.valueOf(str.charAt(0));
			exprs.add(expr);
		}
		for(Expr expr : exprs){
			expr.next = nextExpr(str.substring(expr.text.length()));
		}
		return exprs;
	}
	
}
