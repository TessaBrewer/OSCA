//------------------------------------------------------------------//
//--------------------An abstract User Interface--------------------//
//-Nowhere in our code should we know if we're using a CLI or a GUI-//
//------------------------------------------------------------------//

package io.github.highqualitybean.UI;

public interface UI {
  public void print(String s);
  
  public void println(String s);
  
  public void printerr(String s);
}