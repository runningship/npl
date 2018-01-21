package org.bc.participle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bc.participle.entity.GuessedWord;
import org.bc.participle.entity.Line;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;

/**
 * 
 * 将句子中的词(token)之间的关系定义为边，保存到数据库中
 * 
 * @author xzye
 *
 */
public class LineManager {

	CommonDaoService dao = TransactionalServiceHelper
			.getTransactionalService(CommonDaoService.class);

	private GuessWordManager gwm = new GuessWordManager();

	public List<Line> parseLinesFromTokens(List<String> tokens) {
		List<Line> lines = new ArrayList<Line>();
		if (tokens == null) {
			return lines;
		}
		for (int i = 0; i < tokens.size() - 1; i++) {
			if (String.valueOf(TokenManager.Seperate_Char)
					.equals(tokens.get(i))) {
				continue;
			}
			// 判断是否存在于GuessedWord中
			GuessedWord word = gwm.getWordFromTokens(tokens, i);
			if (word == null) {
				Line line = new Line();
				line.leftToken = tokens.get(i);
				line.rightToken = tokens.get(i + 1);
				line.name = line.leftToken + line.rightToken;
				lines.add(line);
			} else {
				Line line = new Line();
				line.leftToken = word.leftToken;
				line.rightToken = word.rightToken;
				line.name = word.name;
				// remove last
				if (!lines.isEmpty()) {
					// 不该一概而论的移除上一个
					lines.remove(lines.size() - 1);
				}
				lines.add(line);
				i += word.name.length() - 1;
				// 推测下一个词
				if(i<tokens.size()-1){
					Line line2 = new Line();
					line2.leftToken = line.name;
					line2.rightToken = tokens.get(i+1);
					line2.name = line2.leftToken + line2.rightToken;
					lines.add(line2);
				}
			}
			// 下面的方法只处理两个字的词语情况
			// if (GuessedWordCache.isExsistInGuessedWord(tokens.get(i)
			// ,tokens.get(i+1))){
			// if(i+2<tokens.size()){
			// Line line = new Line();
			// line.leftToken = tokens.get(i) + tokens.get(i+1);
			// line.rightToken = tokens.get(i + 2);
			// line.name = line.leftToken + line.rightToken;
			// // remove last
			// if(!lines.isEmpty()){
			// lines.remove(lines.size()-1);
			// }
			// lines.add(line);
			// i++;
			// }
			// }else{
			//
			// }

		}
		return lines;
	}

	public int saveLine(Line line) throws DuplicateTrainingException {
		// ThreadSessionHelper.setDbType(ThreadSessionHelper.H2_Db);
		Line po = dao.getUniqueByParams(Line.class, new String[] { "name",
				"articleId" }, new Object[] { line.name, line.articleId });
		if (po == null) {
			line.score = line.score;
			dao.saveOrUpdate(line);
			return 1;
		} else {
			throw new DuplicateTrainingException();
		}
		// ThreadSessionHelper.restore();
	}
}
