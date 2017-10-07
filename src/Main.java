// Main.java -- the main program

import java.io.*;

public class Main {
    // Array of token names used for debugging the scanner
    public static final String TokenName[] = {
	"QUOTE",			// '
	"LPAREN",			// (
	"RPAREN",			// )
	"DOT",				// .
	"TRUE",				// #t
	"FALSE",			// #f
	"INT",				// integer constant
	"STRING",			// string constant
	"IDENT"				// identifier
    };

    public static void main (String argv[]) {
    	
    Scanner testScanner;
    Scanner realScanner;
	// create scanner that reads from standard input
    if(argv.length == 2 && argv[1] != null) {
    	// test file is specified
    	File f = new File(argv[1]);
    	FileInputStream fs = null;
    	FileInputStream fs2 = null;
    	try {
			fs = new FileInputStream(f);
			fs2 = new FileInputStream(f);
			testScanner = new Scanner(fs);
			realScanner = new Scanner(fs2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to load file from command line. Opening scanner with System.in stream.");
			testScanner = new Scanner(System.in);
			realScanner = new Scanner(System.in);
			e.printStackTrace();
		}
    }
    else if(argv.length == 1 && argv[0] != null) {
    	// test file is specified
    	File f = new File(argv[0]);
    	FileInputStream fs = null;
    	FileInputStream fs2 = null;
    	try {
			fs = new FileInputStream(f);
			fs2 = new FileInputStream(f);
			testScanner = new Scanner(fs);
			realScanner = new Scanner(fs2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to load file from command line. Opening scanner with System.in stream.");
			testScanner = new Scanner(System.in);
			realScanner = new Scanner(System.in);
			e.printStackTrace();
		}
    }
    else {
    	// no test file was specified
    	testScanner = new Scanner(System.in);
    	realScanner = new Scanner(System.in);
    }

	if (argv.length > 2) {
	    System.err.println("Usage: java Main " + "[-d]");
	    System.exit(1);
	}
	
	// if commandline option -d is provided, debug the scanner
	if (argv.length >= 1 && argv[0].equals("-d")) {
		System.out.println("Entering debug scanner mode.");
	    // debug scanner
		Token tok = null;
		try {
			tok = testScanner.getNextToken();
		}
		catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	    while (tok != null) {
		int tt = tok.getType();
		System.out.print(TokenName[tt]);
		if (tt == Token.INT)
		    System.out.println(", intVal = " + tok.getIntVal());
		else if (tt == Token.STRING)
		    System.out.println(", strVal = " + tok.getStrVal());
		else if (tt == Token.IDENT)
		    System.out.println(", name = " + tok.getName());
		else
		    System.out.println();

		try {
			tok = testScanner.getNextToken();
		}
		catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
			tok = null;
		}
	  }
	}
	// Create parser
	Parser parser = new Parser(realScanner);
	Node root;
	
	// Parse and pretty-print each input expression
	// loop should only run twice, once to generate the full tree for recursive printing, and the second time to
	// retrieve EOF info, since at that point, the scanner must return EOF data since there shouldn't be any more data left to parse.
	root = parser.parseExp();
	while (root != null) {
		if(root.isNull()) {
			// parser for some inputs will not catch trailing Nil nodes the first pass around.
			// Grammar implementation probably has some bugs.
			// Temporary fix: we make sure to print ')' if any Nil nodes remain unparsed at the end.
			System.out.print(')');
		}
		else {
			root.print(0);
		}
	    
	    root = parser.parseExp();
	}
	System.exit(0);
    }
}
