// Scanner.java -- the implementation of class Scanner

import java.io.*;

class Scanner {
  private PushbackInputStream in;
  private byte[] buf = new byte[1000];

  public Scanner(InputStream i) { in = new PushbackInputStream(i); }
    
  public Token getNextToken() {
    int bite = -1;
	
    // It would be more efficient if we'd maintain our own input buffer
    // and read characters out of that buffer, but reading individual
    // characters from the input stream is easier.
    try {
      bite = in.read();
    } catch (IOException e) {
      System.err.println("We fail: " + e.getMessage());
    }

    if (bite == -1)
    	return null;
    char ch = (char) bite;
	
    // skipping white space characters
    if(String.valueOf(ch).matches("\\s")) {
    	return getNextToken();
    }
    // skipping comment lines
    else if(ch == ';') {
    	// continue skipping characters until we hit a newline
    	while(ch != '\n') {
    		try {
    		ch = (char)in.read();}
    		catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    	// skip the newline and try the next token
    	return getNextToken();
    }
    // Special characters
    else if (ch == '\'')
      return new Token(Token.QUOTE);
    else if (ch == '(')
      return new Token(Token.LPAREN);
    else if (ch == ')')
      return new Token(Token.RPAREN);
    else if (ch == '.')
      // We ignore the special identifier `...'.
      return new Token(Token.DOT);
     
    // Boolean constants
    else if (ch == '#') {
      try {
    	  bite = in.read();
      }
      catch (IOException e) {
    	  System.err.println("We fail: " + e.getMessage());
      }      
      if (bite == -1) {
		System.err.println("Unexpected EOF following #");
		return null;
      }
      ch = (char) bite;
      if (ch == 't')
    	  return new Token(Token.TRUE);
      else if (ch == 'f')
    	  return new Token(Token.FALSE);
      else {
    	  System.err.println("Illegal character '" + (char) ch + "' following #");
    	  return getNextToken();
      }
    }

    // String constants
    else if (ch == '"') {
      // TODO: scan a string into the buffer variable buf
      return new StrToken(buf.toString());
    }
    // Negative integer constants
    else if(ch == '-') {
    	int sign = -1;
    	try {
    	ch = (char)in.read();
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    		return null;
    	}
    	if(ch >= '0' && ch <= '9') {
    		int i = lexInt(ch)*sign;
    		return new IntToken(i);
    	}
    	else {
    		// Not sure if we should handle the case of the '-' char followed by a non-integer char in such a way way...
    		// guess we'll find out later.
    		System.err.println("Illegal character '" + ch + "' following -");
      	  	return getNextToken();
//    		try {
//    		in.unread(ch);
//    		}
//    		catch(Exception e) {
//    			e.printStackTrace();
//    			return null;
//    		}
//    		return new StrToken("-");
    	}
    }
    // Positive Integer constants
    else if (ch >= '0' && ch <= '9') {
    	int i = lexInt(ch);
    	return new IntToken(i);
		}
		

    // Identifiers
    else if (ch >= 'A' && ch <= 'Z'
	     /* or ch is some other valid first character for an identifier */) {
      // TODO: scan an identifier into the buffer

      // put the character after the identifier back into the input
      // in->putback(ch);
      return new IdentToken(buf.toString());
    }

    // Illegal character
    else {
      System.err.println("Illegal input character '" + (char) ch + '\'');
      return getNextToken();
    }
  }
  
  	// starting from an integer char, this method
  	// advances the scanner to the char after the very last consecutive integer char
  	// so for the sequence '12345.' our scanner would currently be before '.' 
	private Integer lexInt(char ch) {
		int i = ch - '0';	
		int numDigits = 0;
		int counter = 0;
		
		// keeps track of whether we should continue reading from the scanner
		boolean proceed = true;
		// accumulator for our function that translates each digit from a byte to an integer
		int tt = 0;
		// starting from current integer, load each consecutive integer char from scanner into the buffer
		while(proceed){
			buf[counter] = (byte)i;
			counter++;
			numDigits++;
			//System.out.println("digits: " + numDigits);
			
			try {
			ch = (char)in.read();}
			catch(IOException e) {
				e.printStackTrace();
				return null;
			}
			
			// test for if we should try reading the next char from the scanner or if we should quit and putback the current char
			if(ch >= '0' && ch <= '9'){
				i = ch - '0';
				System.out.println(i);
			}
			else{
			//put the current char back into the input stream
				try {
					in.unread(ch);}
				catch(IOException e) {
					e.printStackTrace();
					return null;
					}
				//stop any more reading of the scanner for now
				proceed = !proceed;
				}
		}
		// finished loading byte representation of int literal 
		// translate byte representation of the int literal into a java int type
		counter = 0;	
		while(numDigits > 0){
		tt +=  ((int)buf[counter])*(int)Math.pow(10, numDigits-1);
		counter++;
		numDigits--;
		}
	return new Integer(tt);
	}
}
