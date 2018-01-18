package org.bc.participle;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.bc.participle.entity.GuessedWord;
import org.bc.participle.entity.Line;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;

/**
 * 从Line中过滤可能的词
 * @author yexinzhou
 *
 */
public class GuessWordManager {

	private CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	public void doGuess(){
		List<Line> list = dao.listByParams(Line.class, "from Line where score>10");
		for(Line line : list){
			System.out.println("处理数据: "+line.name);
			moveWord(line);
		}
		// clear rest lines
		dao.execute("delete from Line");
	}

	@Transactional
	private void moveWord(Line line) {
		if(line!=null){
			if(TokenUtils.isChinese(line.name)){
				GuessedWord word = new GuessedWord();
				word.leftToken = line.leftToken;
				word.rightToken = line.rightToken;
				word.name = line.name;
				word.score = 1;
				addWord(word);
			}else{
				System.out.println(line.name+"包含非中文字符");
			}
			removeLine(line);
		}
	}

	private void removeLine(Line line) {
		dao.delete(line);
//		dao.execute("delete from Line where name = ?", line.name);
	}

	private void addWord(GuessedWord word) {
		GuessedWord po = dao.getUniqueByKeyValue(GuessedWord.class, "name", word.name);
		if(po==null){
			dao.saveOrUpdate(word);
		}else{
			po.score++;
			dao.saveOrUpdate(po);
		}
	}
}
