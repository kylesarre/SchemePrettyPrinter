class Cons extends Node {
    private Node car;
    private Node cdr;
    private Special form;
  
    // parseList() `parses' special forms, constructs an appropriate
    // object of a subclass of Special, and stores a pointer to that
    // object in variable form.  It would be possible to fully parse
    // special forms at this point.  Since this causes complications
    // when using (incorrect) programs as data, it is easiest to let
    // parseList only look at the car for selecting the appropriate
    // object from the Special hierarchy and to leave the rest of
    // parsing up to the interpreter.
    void parseList(Node car) {
    	if(car.isSymbol() && ((Ident)car).getName().toLowerCase().equals("begin")) {
    		form = new Begin();
    	}
    	else if(car.isSymbol() && ((Ident)car).getName().toLowerCase().equals("cond")) {
    		form = new Cond();
    	}
    	else if(car.isSymbol() && ((Ident)car).getName().toLowerCase().equals("if")) {
    		form = new If();
    	}
    	else if(car.isSymbol() && ((Ident)car).getName().toLowerCase().equals("lambda")) {
    		form = new Lambda();
    	}
    	else if(car.isSymbol() && ((Ident)car).getName().toLowerCase().equals("let")) {
    		form = new Let();
    	}
    	else if(car.isSymbol() && ((Ident)car).getName().toLowerCase().equals("quote")) {
    		form = new Quote();
    	}
    	else if(car.isSymbol() && ((Ident)car).getName().toLowerCase().equals("set!")) {
    		form = new Set();
    	}
    	else if(car.isSymbol() && ((Ident)car).getName().toLowerCase().equals("define")) {
    		form = new Define();
    	}
    	else {
    		form = new Regular();
    	}
    }
    // TODO: Add any helper functions for parseList as appropriate.
    public boolean isPair() {
    	return true;
    }

    public Cons(Node a, Node d) {
	car = a;
	cdr = d;
	parseList(car);
    }
    
    public Node getCar() {
    	return car;
    }
    
    public Node getCdr() {
    	return cdr;
    }

    void print(int n) {
    	form.print(this, n, false);
    }

    void print(int n, boolean p) {
    	form.print(this, n, p);
    }

}
