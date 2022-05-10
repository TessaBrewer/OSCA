//------------------------//
//A command line interface//
//------------------------//

package io.github.highqualitybean.UI;

public class CLI implements UI {
  public void print(String s) {
    System.out.print(s);
  }
  
  public void printErr(String s) {
    System.err.print(s);
  }
}