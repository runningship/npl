package org.bc.npl.core;

import java.util.ArrayList;
import java.util.List;

import org.bc.npl.QuestionUtil;
import org.bc.npl.Subject;
import org.bc.npl.entity.Fact;
import org.bc.npl.entity.Question;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;

//基础运算符运算器
//是，吗等
public class Caculator2 {

	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	public void eval(Subject sbj , int sentenceId ,  int contextId){
		//先处理属性(的)
		for(Subject attr : sbj.de){
			processAttr(sbj , attr , sentenceId);
		}
		//处理you
		for(Subject val : sbj.you){
			Fact fact = new Fact();
			fact.x = sbj.name;
			fact.f = "有";
			fact.y = val.name;
			fact.sentenceId = sentenceId;
			dao.saveOrUpdate(fact);
			eval(val , sentenceId , contextId);
		}
		//判断是否有疑问句
		List<Fact> list = dao.listByParams(Fact.class, "from Fact where sentenceId=?", sentenceId);
		Fact question = null;
		for(Fact q : list){
			if(QuestionUtil.isQuestionWord(q.y)){
				//raise a question
				question = q;
				break;
			}
		}
		if(question!=null){
			QuestionUtil.moveToQuestion(sentenceId);
			dao.execute("delete from Fact where sentenceId=?", question.sentenceId);
			List<Fact> result = searchY(question.x , question.f , question.sentenceId);
			for(Fact f : result){
				System.out.println(f.x+","+f.f+","+f.y);
			}
		}
	}
	
	private List<Fact> searchY(String x, String f , int sentenceId){
		List<Fact> result = new ArrayList<Fact>();
		List<Fact> list = dao.listByParams(Fact.class, "from Fact where x=? and f=? and sentenceId=?", x, f , sentenceId);
		if(list.isEmpty()){
			//找x的映射函数
			List<Question> list2 = dao.listByParams(Question.class, "from Question where y=? and sentenceId=?",x , sentenceId);
			if(list2.isEmpty()){
				//没有结果
				return result;
			}else{
				if(list2.size()>1){
					System.out.println("y="+x+";sentenceId="+sentenceId+" has more than one result");
				}
				//上下文中查找
				Question map = list2.get(0);
				List<Fact> xList = dao.listByParams(Fact.class, "from Fact where x=? and f=? and y=?", map.x, map.f , map.y);
				for(Fact xx : xList){
					result.addAll(searchY(xx.y , f , xx.sentenceId));
				}
			}
		}else{
			if(list.size()>1){
				System.out.println("x="+x+";f="+f+";sentenceId="+sentenceId+" has more than one result");
			}
			return list;
		}
		return result;
	}

	private void processAttr(Subject sbj, Subject attr , int sentenceId) {
		//判断重复就是判断除了数量之外其他属性
		if(attr.types.isEmpty()){
			//TODO
			System.out.println("不知道"+attr.name+"是什么意思");
			Fact fact = new Fact();
			fact.x = sbj.name;
			fact.f = "未知";
			fact.y = attr.name;
			fact.sentenceId = sentenceId;
			dao.saveOrUpdate(fact);
		}
		for(Subject type : attr.types){
			Fact fact = new Fact();
			fact.x = sbj.name;
			fact.f = type.name;
			fact.y = attr.name;
			fact.sentenceId = sentenceId;
			dao.saveOrUpdate(fact);
		}
	}

}
