package org.bc.participle;

import java.util.List;

import org.bc.npl.StartUpListener;
import org.bc.participle.entity.Line;
import org.junit.Test;

public class LineManagerTest {

	@Test
	public void testParseTokenFromText(){
		StartUpListener.initDataSource();
		TokenManager tm = new TokenManager();
		String str = "认为大渡河在大金川以上有三源：梭磨河、绰斯甲河（上源为青海的杜柯河、多柯河）、足木足河";
		List<String> tokens = tm.parseTokenFromSentence(str);
		LineManager lm = new LineManager();
		List<Line> lines = lm.parseLinesFromTokens(tokens);
		PrintUtils.printlnList(lines);
	}
}
