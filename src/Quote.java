import java.io.*;

class Quote extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.

    void print(Node t, int n, boolean p) {
    	int temp;
		if(n > 1) {
			temp = n;
			n = 0;
		}
		else {
			temp = n;
		}
		if(!p) {
			if(n == 1) {
    			System.out.print(" ");
    		}
			System.out.print("\'(");
		}
		
		t.getCdr().print(n, true);		
    }
}
