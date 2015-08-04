package org.bc.logic;

import java.util.ArrayList;
import java.util.List;

import org.bc.npl.entity.Fact;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;

public class ReasonUtil {

	private static CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	public static List<String> cacuY(Fact fact){
		List<String> yList = new ArrayList<String>();
		//TODO x,f不能为空
		Fact po = dao.getUniqueByParams(Fact.class, new String[]{"x" , "f"}, new Object[]{fact.x , fact.f});
		if(po==null){
//			fact.y=SimplificationUtil.UnKnown;
			//寻找y的替换表达式
			List<Fact> list = dao.listByParams(Fact.class, "from Fact where y=?", fact.f);
			for(Fact f : list){
				List<String> exprs = new ArrayList<String>();
				exprs.add(fact.x);
				exprs.add(f.x);
				exprs.add(f.f);
				List<String> result = SimplificationUtil.simplifyLTR(exprs);
				if(result.size()==1){
					yList.add(result.get(0));
				}
			}
		}else{
			yList.add(po.y);
		}
		return yList;
	}
	
	public static List<String> cacuX(Fact fact){
		List<String> xList = new ArrayList<String>();
		Fact po = dao.getUniqueByParams(Fact.class, new String[]{"f" , "y"}, new Object[]{fact.f , fact.y});
		if(po==null){
			//寻找f的替换表达式
			List<Fact> list = dao.listByParams(Fact.class, "from Fact where y=?", fact.f);
			for(Fact f : list){
				List<String> exprs = new ArrayList<String>();
				exprs.add(f.x);
				exprs.add(f.f);
				exprs.add(fact.y);
				List<String> result = SimplificationUtil.simplifyRTL(exprs);
				if(result.size()==1){
					xList.add(result.get(0));
				}
			}
		}else{
			fact.x = po.x;
		}
		return xList;
	}
	
	public static List<List<String>> cacuF(Fact fact){
		List<List<Fact>> result = findPath(fact.y , fact.x);
		List<List<String>> exprPathList = new ArrayList<List<String>>();
		List<List<String>> simpPathList = new ArrayList<List<String>>();
		for(List<Fact> path : result){
			if(isPathTo(path,fact.x)){
				exprPathList.add(pathToExprList(path));
			}
		}
		//化简exprPathList
		for(List<String> exprs : exprPathList){
			//每个表达式简化
			simpPathList.add(SimplificationUtil.simplifyLTR(exprs));
		}
		return simpPathList;
	}
	
	private static List<String> pathToExprList(List<Fact> path) {
		List<String> exprs = new ArrayList<String>();
		//path是由终点到起点的(即y-->x) ,而表达式需要从x-->y
		//注意这里的exprs不包含头位的x,y只需要中间的各种f就可以
		for(int i=path.size()-1;i>=0;i--){
			exprs.add(path.get(i).f);
		}
		return exprs;
	}

	private static boolean isPathTo(List<Fact>path , String x) {
		if(path.isEmpty()){
			return false;
		}
		return path.get(path.size()-1).x.equals(x);
	}

	//找到从结果y向前到x的所有表达式
	private static List<List<Fact>> findPath(String y , String x){
		List<Fact> list = dao.listByParams(Fact.class, "from Fact where y=?", y);
		List<List<Fact>> pathList = new ArrayList<List<Fact>>();
		for(Fact po : list){
			List<Fact> result = new ArrayList<Fact>();
			if(po.x.equals(x)){
				result.add(po);
				pathList.add(result);
			}else{
				List<List<Fact>> childResult = findPath(po.x,x);
				for(List<Fact> child : childResult){
					child.add(0 , po);
				}
				pathList.addAll(childResult);
			}
		}
		return pathList;
	}
}
