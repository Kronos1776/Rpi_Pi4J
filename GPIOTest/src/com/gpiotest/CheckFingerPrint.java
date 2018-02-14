package com.gpiotest;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class CheckFingerPrint
{
   CheckFingerPrint()
   {
      JFrame fingerPrintFrame = new JFrame("FingerPrint screen");
      JLabel fingerScreenLabel = new JLabel();
   //   fingerScreenLabel.setBounds(50, 20, 100, 3);
     fingerScreenLabel.setBorder(new EmptyBorder(10, 10, 13, 13));   // fits in well in the middle of the screen
    //  fingerPrintFrame.setLayout(new FlowLayout());
     fingerScreenLabel.setFont (fingerScreenLabel.getFont ().deriveFont (19.0f));

      fingerScreenLabel.setText("Place your finger on the fingerprint sensor");
      fingerScreenLabel.setSize(500, 500);
     try
      {
    	String s;
		Runtime rt = Runtime.getRuntime();
		Process fp = rt.exec("python2 /usr/share/doc/python-fingerprint/examples/example_search.py");

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(fp.getInputStream()));
		
        while ((s = stdInput.readLine()) != null) {
        	System.out.println(s);
        	//gives the String for fingerprint
            System.out.println(s.substring(s.lastIndexOf(":") + 1).replaceAll("\\s", ""));
           
        }
      }
      catch (IOException e) {
          System.out.println("exception happened - here's what I know: ");
          e.printStackTrace();
          System.exit(-1);
      }
      fingerPrintFrame.add(fingerScreenLabel);
      fingerPrintFrame.setSize(500, 500);
      fingerPrintFrame.setLayout(null);
      fingerPrintFrame.setLocationRelativeTo(null); //center it or get it away from info frame
      fingerPrintFrame.setVisible(true);
   }
}
