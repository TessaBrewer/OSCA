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
import java.io.File;
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
        String [] elms = currentLine.split("\\s+"); //Split on whitespace
        
        switch(elms[0].toLowerCase()) {
          case "cd":
            cd(elms);
            break;
            
          default:
            //We take this to the subshell iff it isn't one of our commands
            (new SubShell()).exec(currentLine, workingDirectory);
            break;
        }
      }
    }
  }
  
  public void cd(String [] args) {
    if(args.length < 2) {
      printerrln("Please specify a directory");
      return;
    }
    
    switch(args[1]) {
      case ".":
        break;
        
      case "..":
        String [] directoryStructure = workingDirectory.split(Config.get("dir.delimiter"));
        StringBuilder newWorkingDirectory = new StringBuilder();
        for(int i = 0; i < directoryStructure.length - 1; i++) {
          newWorkingDirectory.append(directoryStructure[1]);
          newWorkingDirectory.append(Config.get("dir.delimiter"));
        }
        workingDirectory = newWorkingDirectory.toString();
        break;
        
      default:
        String tempWorkingDirectory = workingDirectory + Config.get("dir.delimiter") + args[1];
        if((new File(tempWorkingDirectory)).isDirectory()) {
          workingDirectory = tempWorkingDirectory;
        } else {
          printerrln("Error: Could not find the path specified");
        }
        break;
    }
  }
}