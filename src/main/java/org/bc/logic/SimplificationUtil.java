package org.bc.logic;

import java.util.ArrayList;
import java.util.List;

import org.bc.npl.entity.Fact;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;

public class SimplificationUtil {
	
	public static final String UnKnown="?";
	private static CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	//表达式化简,化简的结果最好为一个string,但也有可能化简不到最好结果
	//从左到右
	public static List<String> simplifyLTR(List<String> exprs){
		if(exprs.size()<=1){
			return exprs;
		}
		List<String> results = new ArrayList<String>();
		for(int i=0;i<exprs.size();i++){
			if(i==exprs.size()-1){
				results.add(exprs.get(i));
				continue;
			}
			Fact fact = dao.getUniqueByParams(Fact.class, new String[]{"x" , "f"}, new Object[]{exprs.get(i) , exprs.get(i+1)});
			if(fact==null){
				results.add(exprs.get(i));
			}else{
				results.add(fact.y);
				i++;
			}
		}
		if(results.size()==exprs.size()){
			return results;
		}
		return simplifyLTR(results);
	}
	
	//从右到左
	public static List<String> simplifyRTL(List<String> exprs){
		if(exprs.size()<=1){
			return exprs;
		}
		List<String> results = new ArrayList<String>();
		for(int i=exprs.size()-1;i>=0;i--){
			if(i==0){
				results.add(0,exprs.get(i));
				continue;
			}
			Fact fact = dao.getUniqueByParams(Fact.class, new String[]{"y" , "f"}, new Object[]{exprs.get(i) , exprs.get(i-1)});
			if(fact==null){
				results.add(exprs.get(i));
			}else{
				results.add(0, fact.x);
				i--;
			}
		}
		if(results.size()==exprs.size()){
			return results;
		}
		return simplifyRTL(results);
	}
	
}
