// Parser.java -- the implementation of class Parser
//
// Defines
//
//   class Parser;
//
// Parses the language
//
//   exp  ->  ( rest
//         |  #f
//         |  #t
//         |  ' exp
//         |  integer_constant
//         |  string_constant
//         |  identifier
//    rest -> )
//         |  exp R
//
//	  R -> rest
//		-> . exp )
//
// and builds a parse tree.  Lists of the form (rest) are further
// `parsed' into regular lists and special forms in the constructor
// for the parse tree node class Cons.  See Cons.parseList() for
// more information.
//
// The parser is implemented as an LL(0) recursive descent parser.
// I.e., parseExp() expects that the first token of an exp has not
// been read yet.  If parseRest() reads the first token of an exp
// before calling parseExp(), that token must be put back so that
// it can be reread by parseExp() or an alternative version of
// parseExp() must be called.
//
// If EOF is reached (i.e., if the scanner returns a NULL token,
// the parser returns a NULL tree.  In case of a parse error, the
// parser discards the offending token (which probably was a DOT
// or an RPAREN) and attempts to continue parsing with the next token.
// 
//
//
//
class Parser {
  private Scanner scanner;
  
  // constant objects for our pretty printer
  private Nil nil = new Nil();
  private BooleanLit TRUE = new BooleanLit(true);
  private BooleanLit FALSE = new BooleanLit(false);

  public Parser(Scanner s) { scanner = s; }
  
  public Node parseExp() {
	  //System.err.println("Calling parseExp(), forwarding");
	  Token curToken;
		try {
		curToken = scanner.getNextToken();
		}
		catch(Exception e) {
			curToken = null;
			//System.err.println("Failure.");
		}
		return parseExp(curToken);
  }
  
  public Node parseExp(Token curToken) {
	//System.err.println("Calling parseExp(), no forwarding");
    // TODO: write code for parsing an exp

	if(curToken == null) {
		return null;
	}
	else if(curToken.getType() == Token.LPAREN) {
		return parseRest();			 
	}
	else if(curToken.getType() == Token.QUOTE) {
		//System.err.println("QUOTE");
		return new Cons(new Ident("quote"), parseExp());
	}
	else if(curToken.getType() == Token.RPAREN) {
		//System.err.println("NIL!");
		return nil;
	}	
	else if(curToken.getType() == Token.TRUE) {
		return TRUE; // the constant bool object for true
	}
	else if(curToken.getType() == Token.FALSE) {
		return FALSE; // the constant bool object for false
	}
	else if(curToken.getType() == Token.INT) {
		return new IntLit(curToken.getIntVal());
	}
	else if(curToken.getType() == Token.STRING) {
		return new StrLit(((StrToken)curToken).getStrVal());
	}
	else if(curToken.getType() == Token.IDENT) {
		//System.err.println(curToken.getName());
		return new Ident(((IdentToken)curToken).getName());
	}
	else {
			//System.err.println("Parser error under parseExp(). unexpected token type: " + curToken.getType());
			//System.err.println("Ignoring token.");
			return parseExp();
	}
  }
  
  protected Node parseRest() {
	  //System.err.println("Calling parseRest(), forwarding");
	  Token curToken;
		// grab the next token to see what the "rest" is.
		try {
			curToken = scanner.getNextToken();}
			catch(Exception e) {
				curToken = null;
			}
		return parseRest(curToken);
  }
  
  protected Node parseRest(Token curToken) {
    // TODO: write code for parsing rest
	 //System.err.println("Calling parseRest(), no forwarding");
	if(curToken == null) {
		return null;
	}
	else if(curToken.getType() == Token.RPAREN) {
		//System.err.println("NIL!");
		return nil;
	}
	else if(curToken.getType() == Token.LPAREN) {
		//System.err.println("CONS");
		return new Cons(parseExp(curToken),parseRest());
	}
	else if(curToken.getType() == Token.DOT) {
		//System.out.println("Returning null!");
		return null;
	}
	else {
		//return new Cons(parseExp(curToken), parseFinal());
		return parseFinal(parseExp(curToken));
	}	
  }
  
  protected Node parseFinal(Node exp) {
	  //System.err.println("Calling parseFinal(), forwarding");
	  Token curToken;
	  try {
			curToken = scanner.getNextToken();}
			catch(Exception e) {
				curToken = null;
			}
	  return parseFinal(exp, curToken);
  }
  
  protected Node parseFinal(Node exp, Token curToken) {
	  //System.err.println("Calling parseFinal(), no forwarding");
	  if(curToken == null) {
		  return null;
	  }
	  else if(curToken.getType() == TokenType.DOT ) {
		  //System.err.println("DOT");
		  return new Cons(exp, parseExp());
	  }
	  else if(curToken.getType() == TokenType.RPAREN) {
		  return new Cons(exp, parseRest(curToken));
	  }
	  else {
		  //System.err.println("CONS");
		  return new Cons(exp, parseRest(curToken));
	  }
  }
  // TODO: Add any additional methods you might need.
};
