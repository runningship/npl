package org.bc.antr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.atn.ATN;

public class DynamicLexer extends Lexer{

	@Override
	public ATN getATN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGrammarFileName() {
		return "自然语言";
	}

	@Override
	public String[] getRuleNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
