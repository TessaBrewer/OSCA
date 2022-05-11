//-----------------------------------------------------//
//--------All flags get loaded into here by main-------//
//Can be accessed by entire program to change behaviour//
//---------Essentially a global set of booleans--------//
//-----------------------------------------------------//

package io.github.highqualitybean;

public class Flags{
  public static boolean verbose = false;
  
  public static void set(String[] args) {
    for(String s : args) {
      switch(normalize(s)) {
        case "-v":
        case "-verbose":
          verbose = true;
          break;
      }
    }
  }
  
  private static String normalize(String s) {
    if(s.charAt(0) == '-' && s.charAt(1) == '-') {
      s = s.substring(1, s.length());
    }
    
    return s.toLowerCase();
  }
}