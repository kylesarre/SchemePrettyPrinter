import java.io.*;
import java.util.ArrayList;

class Begin extends Special {
 
    // TODO: Add any fields needed.
	public Begin() {
		
	}
 
    // TODO: Add an appropriate constructor.

    void print(Node t, int n, boolean p) {
    	if(!p) {
    		if(n >= 1) {
    			System.out.print(" ");
    		}
			System.out.print("(begin");
			p = true;
		}   	
    	int temp = n;
    	
    	Node car = t.getCar();
    	Node cdr = t.getCdr();
    	
    	if(cdr.getCar().isPair()) {
			//prints begin on one line; every additional element is printed on the next line with 4 indentations.
			ArrayList<Node> nodes = getElements(cdr.getCdr(), new ArrayList<Node>());
			n += 4;
			System.out.print("\n");
			for(int i = 0; i < n; i++) {
				System.out.print(" ");
			}
			if(cdr.getCar().isPair()) {
				System.out.print("(");
				((Cons)cdr.getCar()).print(n, true);
			}
			else {
				cdr.print(n);
			}
			for(Node node: nodes){
				System.out.print("\n");
				if(node.isPair()) {
					if(node.getCdr().isNull()) {
						n -= 4;							
					}
				}
				else if(node.isNull()) {
					n -= 4;
				}
				for(int i = 0; i < n; i++) {
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
