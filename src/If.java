import java.io.*;
import java.util.ArrayList;

class If extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.

    void print(Node t, int n, boolean p) {
    	if(!p) {
    		if(n == 1) {
    			System.out.print(" ");
    		}
			System.out.print("(");
			p = true;
		}   	
    	int temp = n;
    	n = 0;
		t.getCar().print(n);
		Node cdr = ((Cons)t).getCdr();
		if(cdr.isPair()) {
    	//function definition print only the first two expressions on one line.
		//additional expressions are printed on a newline indented with 4 white space.
		System.out.print(" ");
		cdr.getCar().print(n, false);
		//define and pair printed, now we need to print the rest of the function definition according to the specification
		ArrayList<Node> nodes = getElements(cdr.getCdr(), new ArrayList<Node>());
		temp += 4;
		for(Node node: nodes){
			System.out.print("\n");
			if(node.isPair()) {
				if(node.getCdr().isNull()) {
					temp -= 4;							
				}
			}
			else if(node.isNull()) {
				temp -= 4;	
			}
			for(int i = 0; i < temp; i++) {
				System.out.print(" ");
			}		
			if(!node.isNull()) {
				node.print(n, false);
			}
			else {
				node.print(0, true);
			}
		}
	}	
  }
    
  //we need a shallow reference to the function definition's list elements.
    //this way we can apply a shallow indentation that won't leak over into other special print forms.
    private ArrayList<Node> getElements(Node cdr, ArrayList<Node> emptyList) {
    	if(cdr == null) {
    		System.err.print("parse error! expected a closing parenthesis or an expression but encountered null!");
    		return emptyList;
    	}
    	if(cdr.isNull()) {
    		emptyList.add(cdr);
    		return emptyList;
    	}
    	else if(cdr.isPair()){
    		emptyList.add(cdr.getCar());
    		return getElements(cdr.getCdr(), emptyList);
    	}
    	else {
    		emptyList.add(cdr);
    		return emptyList;
    	}
    }
}
