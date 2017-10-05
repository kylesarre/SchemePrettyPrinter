import java.io.*;

class Regular extends Special {
 
    // TODO: Add any fields needed.
	
 
    // TODO: Add an appropriate constructor.

    void print(Node t, int n, boolean p) {
    	if(t.isPair()) {
    		if(p) {
    			((Cons)t).getCar().print(n);
        		((Cons)t).getCdr().print(n, p);
    		}
    		else {
    			System.out.print('(');
    			Node car = ((Cons)t).getCar();
    			Node cdr = ((Cons)t).getCdr();
    			if(car != null) {
    				if(car.isPair())
    					car.print(n, false);
    				else if(car.isNull()) {
    					car.print(n, false);
    				}
    				else {
    					car.print(n);
    				}
    			}
    			if(cdr != null) {
    				if(cdr.isPair()) {
    					car.print(n, false);
    				}
    				else if(cdr.isNull()) {
    					car.print(n, false);
    				}
    				else {
    					cdr.print(n);
    				}   				
    			}
    		}
    	}
    }
}
