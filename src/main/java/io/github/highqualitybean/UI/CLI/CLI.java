//------------------------//
//A command line interface//
//------------------------//

package io.github.highqualitybean.UI.CLI;

import io.github.highqualitybean.UI.*;
import io.github.highqualitybean.Config;

import java.util.Scanner;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class CLI implements UI {
  private String workingDirectory;
  private Thread commandLineListener;
  private Listener flags;
  private int terminalWidth = Config.getInt("terminal.width");
  
  public CLI() {
    workingDirectory = System.getProperty("user.dir");
    
    welcomeMessage(terminalWidth);
    
    commandLineListener = new Thread(new Listener());
    commandLineListener.start();
  }
  
  public void print(String s) {
    System.out.print(s);
  }
  
  public void printCenter(String s) {
    int width = terminalWidth;
    
    Scanner scanner = new Scanner(s);
    while(scanner.hasNextLine()) {
      int lineWidth = scanner.nextLine().length();
      if(lineWidth > width)
        width = lineWidth;
    }
    
    scanner = new Scanner(s);
    while(scanner.hasNextLine()) {
      String line = scanner.nextLine();
      
      int lineWidth = width;
      lineWidth -= line.length();
      lineWidth = Math.floorDiv(lineWidth, 2);
      
      line = String.join("", Collections.nCopies(lineWidth, " ")) + line;
      println(line);
    }
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

  private void welcomeMessage(int terminalWidth) {
    String fancyText = "\n  ___           ___           ___           ___\n  /\\  \\         /\\  \\         /\\  \\         /\\  \\\n  /::\\  \\       /::\\  \\       /::\\  \\       /::\\  \\\n  /:/\\:\\  \\     /:/\\ \\  \\     /:/\\:\\  \\     /:/\\:\\  \\\n  /:/  \\:\\  \\   _\\:\\~\\ \\  \\   /:/  \\:\\  \\   /::\\~\\:\\  \\\n  /:/__/ \\:\\__\\ /\\ \\:\\ \\ \\__\\ /:/__/ \\:\\__\\ /:/\\:\\ \\:\\__\\\n  \\:\\  \\ /:/  / \\:\\ \\:\\ \\/__/ \\:\\  \\  \\/__/ \\/__\\:\\/:/  /\n  \\:\\  /:/  /   \\:\\ \\:\\__\\    \\:\\  \\            \\::/  /\n  \\:\\/:/  /     \\:\\/:/  /     \\:\\  \\           /:/  /\n  \\::/  /       \\::/  /       \\:\\__\\         /:/  /\n  \\/__/         \\/__/         \\/__/         \\/__/";
    printCenter("Welcome to");
    printCenter(fancyText);
    printCenter("The Open Source Code Analyser");
  }
  
  protected class Listener implements Runnable {
    Scanner in = new Scanner(System.in);
    String currentLine = "";
    boolean breakFlag = false;
    
    public void run() {
      while(!breakFlag) {
        System.out.print("OSCA " + workingDirectory + "> ");
        currentLine = in.nextLine();
        System.out.println(currentLine);
      }
    }
  }
}