//------------------------//
//A command line interface//
//------------------------//

package io.github.highqualitybean.UI;

public class CLI implements UI {
  public void print(String s) {
    System.out.print(s);
  }
  
  public void println(String s) {
    this.print(s + "\n");
  }
  
  public void printerr(String s) {
    System.err.print(s);
  }
  
  public void printerrln(String s) {
    this.printerr(s + "\n");
  }
}