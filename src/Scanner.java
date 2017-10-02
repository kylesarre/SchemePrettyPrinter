// Scanner.java -- the implementation of class Scanner

import java.io.*;
import Exceptions.IllegalTokenException;

class Scanner {
  private PushbackInputStream in;
  private byte[] buf = new byte[1000];

  public Scanner(InputStream i) { in = new PushbackInputStream(i); }
    
  public Token getNextToken() throws IllegalTokenException {
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
      int length = 0;
      try {
      bite = in.read();}
      catch(Exception e) {
    	  e.printStackTrace();
    	  return null;
      }
      //make sure the next char we read isn't EOF. If it is, we have a language error
      if(bite != -1) {
    	  ch = (char)bite;
      }
      else {
    	  throw new Exceptions.IllegalTokenException("Lexing error: expected a string literal but encountered EOF.");  
      }
      // Next char seems to be a non EOF char. store each char read from the input stream until we encounter another '"' char or hit EOF.
      while(ch != '"') {
    	  buf[length] = (byte)ch;
    	  try {
    	      bite = in.read();}
    	      catch(Exception e) {
    	    	  e.printStackTrace();
    	    	  return null;
    	      }
    	  if(bite != -1) {
    		  ch = (char)bite;
    		  
    	  }
    	  else {
    		  // EOF reached before we encountered another '"'. This is a clear language error.
    		  throw new Exceptions.IllegalTokenException("Lexing error: expected a string literal but encountered EOF.");
    	  }
    	  length++;    	  
      }
      char[] stringConstant = new char[length];
      // write the data over to a char array and return the string literal token using that char array
      while(length > 0) {
    	  stringConstant[length-1] = (char)buf[length-1];
    	  length--;
      }
      return new StrToken(new String(stringConstant));
    }
    // Negative integer constants
    else if(ch == '-') {
    	char temp = ch;
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
    	// this is actually the - identifier
    	else if(String.valueOf(ch).matches("\\s")) {
    		return new IdentToken(String.valueOf(temp));
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
    else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <='z'))
	     /* or ch is some other valid first character for an identifier */ {
      // TODO: scan an identifier into the buffer
    	//for now just allowing a sequence of alphanumerical characters starting with an alphabetical character
    	int length = 0;
    	while((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <='z') || (ch >= '0' && ch <='9')) {
    		buf[length] = (byte)ch;
    		length++;
    		try {
    			ch = (char)in.read();
    		}
    		catch(IOException e) {
    			e.getMessage();
    			e.printStackTrace();
    		}
    	}
    	try {
    	in.unread((byte)ch);}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	char[] ident = new char[buf.length];
    	for(int i = 0; i < length; i++) {
    		ident[i] = (char)buf[i];
    	}

      // put the character after the identifier back into the input
      // in->putback(ch);
      return new IdentToken(new String(ident));
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
