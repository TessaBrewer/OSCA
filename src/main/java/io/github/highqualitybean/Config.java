//-----------------------------------------------//
//-------Handles everything settings wise--------//
//--Should handle both writing to the save file--//
//----------and loading settings from it---------//
//-----------------------------------------------//

package io.github.highqualitybean;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class Config {
  private static String filename = "settings.tsv";
  private static Properties properties = new Properties();
  
  public static void set() {
    set(filename);
  }
  
  public static void set(String fn) {
    filename = fn;
    try {
      properties.load(new FileReader(filename));
    } catch(IOException e) {
      Main.ui.printerrln("Error loading configuration file");
      if(Flags.verbose){
        Main.ui.printerrln("Stack trace:");
        Main.ui.printerrln(ExceptionUtils.getStackTrace(e));
      }
    }
  }
  
  public synchronized static String get(String s) throws NullPointerException {
    if(properties.isEmpty())
      set();
    return properties.getProperty(s);
  }
  
  public synchronized static int getInt(String s) throws NullPointerException {
    return Integer.parseInt(get(s));
  }
}