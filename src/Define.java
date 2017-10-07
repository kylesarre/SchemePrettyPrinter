import java.io.*;

class Define extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.

    void print(Node t, int n, boolean p) {
    	if(!p) {
			System.out.print("(");
			p = true;
		}   	
		t.getCar().print(n);
		Node cdr = ((Cons)t).getCdr();
		if(cdr.isPair()) {
			if(cdr.getCar().isSymbol()) {
				// variable definition. print all on one line
				System.out.print(" ");
				cdr.getCar().print(n);
				System.out.print(" ");
				if(!cdr.getCdr().isNull()) {
					if(cdr.getCdr().isPair())
					cdr.getCdr().print(n, true);
				}
				else {
					((Nil)cdr).print(n, true);
				}
			}
			else if(cdr.getCar().isPair()) {
				//function definition print only the first two expressions on one line.
				//additional expressions are printed on a newline indented with 4 white space.
				
			}
		}

    }
}
