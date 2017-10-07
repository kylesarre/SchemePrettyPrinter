import java.io.*;

class If extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.

    void print(Node t, int n, boolean p) {
    	if(t != null) {
    		if(!p) {
    			System.out.print('(');
    		}
    		Node car = ((Cons)t).getCar();
    		Node cdr = ((Cons)t).getCdr();
    		if(car.isPair()) {
    			((Cons)car).print(n, true);
    		}
    		else{
    			if(car.isSymbol()) {
    				((Ident)car).print(n+1);
    			}
    			else if(car.isBoolean()) {
    				((BooleanLit)car).print(n);
    			}
    			else if(car.isString()) {
    				((StrLit)car).print(n);
    			}
    			else if(car.isNumber()) {
    				((IntLit)car).print(n);
    			}
    			else if(car.isNull()) {
    				((Nil)car).print(n);
    			}
    		}
    		if(cdr.isPair()) {
    			// might need to check and see if the car of the cdr is a special ident.
    			// Not sure if these have unique prints compared to non-special idents.
    			((Cons)car).print(n, true);
    		}
    		else {
    			if(cdr.isSymbol()) {
    				((Ident)cdr).print(n);
    			}
    			else if(cdr.isBoolean()) {
    				((BooleanLit)cdr).print(n);
    			}
    			else if(cdr.isString()) {
    				((StrLit)cdr).print(n);
    			}
    			else if(cdr.isNumber()) {
    				((IntLit)cdr).print(n);
    			}
    			else if(cdr.isNull()) {
    				((Nil)cdr).print(n);
    			}
    		}
    	}
    }
    }
}
