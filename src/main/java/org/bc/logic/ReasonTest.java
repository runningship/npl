package org.bc.logic;

import java.util.List;

import org.bc.npl.StartUpListener;
import org.bc.npl.entity.Fact;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;

public class ReasonTest {

	private static CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	public static void main(String[] args){
		StartUpListener.initDataSource();
		Fact f1 = new Fact();
		f1.x = "A";
		f1.f="爸爸";
		f1.y = "B";
		add(f1);
		
		Fact f2 = new Fact();
		f2.x = "B";
		f2.f="爸爸";
		f2.y = "C";
		add(f2);
		
		Fact f3 = new Fact();
		f3.x = "爸爸";
		f3.f="爸爸";
		f3.y = "爷爷";
		add(f3);
		
		testQuestionF();
		testQuestionX();
		testQuestionY();
	}

	private static void testQuestionX(){
		Fact question = new Fact();
		question.f="爷爷";
		question.y="C";
		List<String> xList = ReasonUtil.cacuX(question);
		for(String x: xList){
			System.out.println(question.y+"是"+x+"的"+question.f);
		}
	}
	
	private static void testQuestionY(){
		Fact question = new Fact();
		question.x="A";
		question.f="爷爷";
		List<String> yList = ReasonUtil.cacuY(question);
		for(String y: yList){
			System.out.println(question.x+"的"+question.f+"是"+y);
		}
	}
	
	private static void testQuestionF(){
		Fact question = new Fact();
		question.x="A";
		question.y="C";
		List<List<String>> pathList = ReasonUtil.cacuF(question);
		//可能有多个F,而每个F可能由多个小f组成
		
		for(List<String> fList: pathList){
			StringBuilder sb = new StringBuilder(question.y+"是"+question.x);
			for(String f : fList){
				sb.append("的").append(f);
			}
			if(fList.size()>0){
				System.out.println(sb.toString());
			}
		}
	}
	
	private static void add(Fact fact) {
		Fact po = dao.getUniqueByParams(Fact.class, new String[]{"x" ,"f", "y"}, new Object[]{fact.x ,fact.f, fact.y});
		if(po==null){
			dao.saveOrUpdate(fact);
		}
	}
}
