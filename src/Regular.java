import java.io.*;

class Regular extends Special {
 
    // TODO: Add any fields needed.
	
 
    // TODO: Add an appropriate constructor.
	
	// if the bottom most cdr is a literal, then we need to prepare for printing a dot
	// if the subtree containing the dot cdr is n deep relative to its parent, we need n dots.
	// this function allows us to look ahead to determine if the current cons being printed is in fact a dot cons.
	public boolean detectDot(Node t) {
		if(t == null) {
			return true;
		}
		else {
			if(t.isPair()) {
				return detectDot(t.getCdr());
			}
			else if(t.isNull()) {
				return false;
			}
			else {
				return true;
			}
		}		
	}

    void print(Node t, int n, boolean p) {
    	if(t.isPair()) {    		
    		if(!p) {
    			if(n == 1) {
    				System.out.print(" ");
    				n = 0;
    			}
    			System.out.print('(');
    		}
    		boolean isDotCons = detectDot(t);
			Node car = ((Cons)t).getCar();
			Node cdr = ((Cons)t).getCdr();
			
			if(car.isPair()) {					
				car.print(n, false);	
			}
			else if(car.isSymbol()) {
				car.print(n);
			}
			else if(car.isString()) {
				StrLit sl = ((StrLit)car);
				sl.print(n);
			}
			else if(car.isBoolean()) {
				BooleanLit bl = ((BooleanLit)car);
				bl.print(n);
			}
			else if(car.isNumber()) {
				// otherwise must be an integer literal
				IntLit il = ((IntLit)car);
				il.print(n);
			}
			else {
				((Nil)car).print(n, false);
			}
			if(isDotCons) {
				System.out.print(" . ");
			}
			if(cdr == null) {
				
			}
			else {
				if(cdr.isNull()) {	
					int newN = 0;
					((Nil)cdr).print(newN, true);
				}
				else if(cdr.isPair()) {
					int newN = 1;
					cdr.print(newN, true);
				}
				else {
					cdr.print(n);
				}
			}			
    	}
    	else {
    		System.out.print("?");
    	}
    }
}
