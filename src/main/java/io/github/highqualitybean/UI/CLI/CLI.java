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
import java.util.Date;
import java.io.IOException;
import java.io.File;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.text.SimpleDateFormat;

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
            
          case "dir":
            dir();
            break;
            
          case "quit":
          case "exit":
          case "q":
            breakFlag = true;
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
        String delim = Config.get("dir.delimiter");
        String [] directoryStructure = workingDirectory.split(delim.replace("\\", "\\\\"));
        if(directoryStructure.length <= 1)
          return;
        StringBuilder newWorkingDirectory = new StringBuilder();
        int i = 0;
        for(i = 0; i < directoryStructure.length - 2; i++) {
          newWorkingDirectory.append(directoryStructure[i]);
          newWorkingDirectory.append(Config.get("dir.delimiter"));
        }
        newWorkingDirectory.append(directoryStructure[i]);
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
  
  public void dir() {
    String[] subFiles = (new File(workingDirectory)).list();
    
    println("Type\tPerms\t    Last Write Time     \tLength\tName");
    println("----\t----\t------------------------\t------\t----");
    
    for(String fileName : subFiles) {
      File currentFile = new File(fileName);
      
      String type = "";
      if(currentFile.isFile())
        type="File";
      if(currentFile.isDirectory())
        type="Dir";
      
      String perms = "";
      if(currentFile.canExecute()) {
        perms += "X";
      } else {
        perms += "-";
      }
      if(currentFile.canRead()) {
        perms += "R";
      } else {
        perms += "-";
      }
      if(currentFile.canWrite()) {
        perms += "W";
      } else {
        perms += "-";
      }
      if(currentFile.isHidden()) {
        perms += "H";
      } else {
        perms += "-";
      }
      perms += " ";
      
      long lastWriteMillis = currentFile.lastModified();
      Date date = new Date(lastWriteMillis);
      SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS");
      String lastWriteDate = df.format(date);
      
      String length = (currentFile.isFile() ? (String.valueOf(currentFile.length()) + "B") : ("NA"));
      
      println(type + "\t" + perms + "\t" + lastWriteDate + "\t" + length + "\t" + fileName);
    }
  }
}