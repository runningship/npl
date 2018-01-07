package org.bc.antr;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.v4.runtime.Lexer;


public class TokenTest {

	public static void main(String[] args) throws Exception {
	    String source = "foo bar baz Mu";
	    java.util.Set<String> set = new java.util.HashSet<String>();
	    set.add("Mu");
	    set.add("bar");
//	    Lexer lexer = new Lexer(new ANTLRStringStream(source), set);
//	    TParser parser = new TParser(new CommonTokenStream(lexer));
//	    parser.parse();
	  }
}
