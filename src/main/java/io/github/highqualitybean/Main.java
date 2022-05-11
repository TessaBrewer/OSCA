package io.github.highqualitybean;

import io.github.highqualitybean.UI.*;
import io.github.highqualitybean.*;

public class Main 
{
  public static UI ui;
  
  public static void main(String[] args)
  {
    //Set up the flags
    Flags.set(args);
    
    if(System.console() != null) {
      ui = new CLI();
    } else {
      ui = new GUI();
    }
  }
}
