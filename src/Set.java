import java.io.*;

class Set extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.

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
    		boolean signFlag = false;
    		int temp;
    		if(n > 1) {
    			temp = n;
    			n = 0;
    		}
    		else if(n < 0) {
    			signFlag = true;
    			temp = -1*n;   			
    			n = 1;
    		}
    		else {
    			temp = n;
    		}
    		if(!p) {
    			if(n == 1) {
        			System.out.print(" ");
        		}
    			System.out.print('(');
    		}
    		boolean isDotCons = detectDot(t);
			Node car = ((Cons)t).getCar();
			Node cdr = ((Cons)t).getCdr();
			
			if(car.isPair()) {	
				if(signFlag) {
					car.print(1, false);
				}
				else {
					car.print(1, false);
				}
					
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
					if(cdr.getCar().isPair()) {
						int newN = 1;
						System.out.print(" (");
						cdr.getCar().getCar().print(0);
						cdr.getCar().getCdr().print(newN, true);
						if(cdr.getCdr().isPair()) {
							cdr.getCdr().print(newN, true);
						}
						else if(cdr.getCdr().isNull()){
							((Nil)cdr.getCdr()).print(0, true);;
						}
						else {
							cdr.getCdr().print(newN);
							System.out.print(")");
						}
					}
					else {
						int newN = 1;
						cdr.print(newN, true);
					}					
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
