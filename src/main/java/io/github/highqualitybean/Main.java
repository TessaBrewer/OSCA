package io.github.highqualitybean;

import io.github.highqualitybean.UI.*;

public class Main 
{
  private static UI ui;
  
  public static void main(String[] args)
  {
    if(System.console() != null) {
      ui = new CLI();
    } else {
      ui = new GUI();
    }
  }
}
