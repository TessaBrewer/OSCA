package io.github.highqualitybean.UI.CLI;

import io.github.highqualitybean.*;

import java.io.*;

public class SubShell {
  public void exec(String cmd, String workingDirectory) {
    try {
      Process process = Runtime.getRuntime().exec(cmd, null, new File(workingDirectory));
      
      while(process.isAlive()) {
        InputStream pOut = process.getInputStream();
        InputStream pErr = process.getInputStream();
        OutputStream pIn = process.getOutputStream();
        
        //Connect the process stdout to our out
        while(pOut.available() != 0)
          Main.ui.print(Character.toString((char)pOut.read()));
        //Connect the process stderr to our err
        while(pErr.available() != 0)
          Main.ui.print(Character.toString((char)pErr.read()));
        //Connect our in to the process stdin
        while(System.in.available() != 0)
          pIn.write(System.in.read());
      }
      
    } catch(SecurityException e) {
      Main.ui.printerrln("Error, subprocess could not be created");
    } catch(IOException e) {
      Main.ui.printerrln("An I/O error occurred");
    } catch(NullPointerException e) {
      Main.ui.printerrln("Command was null");
    } catch(IllegalArgumentException e) {
      Main.ui.printerrln("Command was empty");
    }
  }
}