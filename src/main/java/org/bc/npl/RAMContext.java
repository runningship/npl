package org.bc.npl;

import java.util.ArrayList;
import java.util.List;

public class RAMContext {

	//TODO 要用并发
	private List<Subject> sbjList = new ArrayList<Subject>();
	public void addSubject(Subject sbj){
		onNewSentence(sbj);
	}

	private void onNewSentence(Subject sbj) {
		Subject q =getQuestionSubject(sbj); 
		if(q!=null){
			List<Subject> query = QuestionUtil.toQuery(q);
			Subject answer = findAnswer(query);
			if(answer==null){
				//我不知道
			}
			System.out.println(answer);
		}else{
			//合并到上下文
			sbjList.add(sbj);
		}
	}
	
	private Subject findAnswer(List<Subject> query) {
		List<Subject> tmp = sbjList;
		for(Subject qi : query){
			List<Subject> result = findResultByExample(tmp , qi);
			tmp = result;
		}
		//此时tmp为结果
		if(tmp.isEmpty()){
			return null;
		}
		return tmp.get(0);
	}

	public Subject find(Subject example){
		for(Subject sbj : sbjList){
			if(example.name.equals(sbj.name)){
				return sbj;
			}
		}
		return null;
	}
	public List<Subject> findResultByExample(List<Subject> sbjs, Subject qi) {
		List<Subject> result = new ArrayList<Subject>();
		for(Subject sbj : sbjs){
			if("有".equals(qi.name)){
				//就取第一个
				return sbj.you;
			}
			if("的".equals(qi.name)){
				return sbj.de;
			}
			if("?".equals(qi.name)){
				for(Subject qiType :qi.types){
					for(Subject type : sbj.types){
						if(type.name.equals(qiType.name)){
							result.add(sbj);
							return result;
						}
					}
				}
				
			}
			if(qi.name.equals(sbj.name)){
				result.add(sbj);
				return result;
			}
		}
		return result;
	}

	private Subject getQuestionSubject(Subject sbj){
		if(QuestionUtil.isQuestionWord(sbj.name)){
			Subject s = new Subject();
			s.name = "?";
			s.de = sbj.de;
			s.types = sbj.types;
			s.you = sbj.you;
			s.deParent = sbj.deParent;
			s.youParent = sbj.youParent;
			return s;
		}
		for(Subject obj : sbj.you){
			Subject q =getQuestionSubject(obj); 
			if(q!=null){
				return q;
			}
		}
		for(Subject obj : sbj.de){
			Subject q =getQuestionSubject(obj); 
			if(q!=null){
				return q;
			}
		}
		return null;
	}
}
