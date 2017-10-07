import java.io.*;
class Ident extends Node {
  private String name;

  public Ident(String n) { name = n; }

  public void print(int n) {
	  System.out.flush();
    for (int i = 0; i < n; i++) {
      System.out.print(" ");
      }
    System.out.print(name);
  }
  
  public String getName() {
	  return name;
  }
  
  public boolean isSymbol() {
	  return true;
  }
}
