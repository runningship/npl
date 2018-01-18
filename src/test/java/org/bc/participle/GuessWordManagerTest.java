package org.bc.participle;

import org.bc.npl.StartUpListener;
import org.junit.Test;

public class GuessWordManagerTest {

	@Test
	public void testGuessAll(){
		StartUpListener.initDataSource();
		GuessWordManager gwm = new GuessWordManager();
		gwm.doGuess();
	}
}
