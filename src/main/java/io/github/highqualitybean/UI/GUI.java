//---------------------------------//
//A graphical interface using swing//
//---------------------------------//

package io.github.highqualitybean.UI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class GUI implements UI {
  private JFrame jframe;
  
  private JTextPane mainTextArea;
  private StyledDocument mainText;
  
  private JTextArea err;
  
  public GUI() {
    GridBagConstraints c;
    
    //Build the main frame
    jframe = new JFrame("OSCA");
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jframe.setSize(1000, 1000);
    
    //Build the main content pane
    Container mainPane = jframe.getContentPane();
    mainPane.setLayout(new GridBagLayout());
    
    //Build & add the area for the main text
    mainTextArea = new JTextPane();
    mainText = mainTextArea.getStyledDocument();
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.gridheight = 1;
    c.fill = GridBagConstraints.BOTH;
    jframe.add(mainTextArea, c);
    
    //Build & add the area for error text
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.gridheight = 1;
    c.fill = GridBagConstraints.BOTH;
    err = new JTextArea();
    jframe.add(err, c);
    
    //Final frame stuff
    jframe.pack();
    jframe.setVisible(true);
  }
  
  public void print(String s) {
    try {
      mainText.insertString(mainText.getLength(), s, null);
    } catch (BadLocationException exc) {
      this.printErr(s);
    }
  }
  
  public void printErr(String s) {
    err.append(s);
  }
}