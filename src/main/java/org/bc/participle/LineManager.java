package org.bc.participle;

import java.util.ArrayList;
import java.util.List;

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

	public void saveLineForTokens(List<String> tokens) {
		if (tokens == null) {
			return;
		}
		List<Line> lines = parseLinesFromTokens(tokens);
		for (Line line : lines) {
			saveLine(line);
		}
	}

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
			if (String.valueOf(TokenManager.Seperate_Char).equals(
					tokens.get(i + 1))) {
				continue;
			}
			Line line = new Line();
			line.leftToken = tokens.get(i);
			line.rightToken = tokens.get(i + 1);
			line.name = line.leftToken + line.rightToken;
			lines.add(line);
		}
		return lines;
	}

	public int saveLine(Line line) {
//		ThreadSessionHelper.setDbType(ThreadSessionHelper.H2_Db);
		Line po = dao.getUniqueByParams(Line.class, new String[] { "name",
		"articleId" }, new Object[] { line.name, line.articleId });
		try {
			if (po == null) {
				line.score = line.score;
				dao.saveOrUpdate(line);
				return 1;
			} else {
				return 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
//		ThreadSessionHelper.restore();
	}
}
