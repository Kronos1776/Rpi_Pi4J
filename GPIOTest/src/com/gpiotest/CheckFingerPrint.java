package com.gpiotest;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class CheckFingerPrint
{
   CheckFingerPrint()
   {
	     JFrame inmateIdFrame = new JFrame("Button Example");
/*      JFrame fingerPrintFrame = new JFrame("FingerPrint screen");
      JLabel fingerScreenLabel = new JLabel();
   //   fingerScreenLabel.setBounds(50, 20, 100, 3);
     fingerScreenLabel.setBorder(new EmptyBorder(10, 10, 13, 13));   // fits in well in the middle of the screen
    //  fingerPrintFrame.setLayout(new FlowLayout());
     fingerScreenLabel.setFont(fingerScreenLabel.getFont().deriveFont(19.04f));
     

	     inmateIdFrame.setFont (fingerScreenLabel.getFont ().deriveFont (19.0f));
	     fingerScreenLabel.setText("Place your finger on the fingerprint sensor");

	      fingerScreenLabel.setSize(500, 500);
	      inmateIdFrame.add(fingerScreenLabel);
	      inmateIdFrame.setVisible(true);*/
	     
	     try 
	     {
			search(inmateIdFrame);
			
			
		} catch (IOException e9) 
		{
			// TODO Auto-generated catch block
			e9.printStackTrace();
		}
   }
	public static void search(JFrame inmateIdFrame) throws IOException 
	{

		String s = null;
		String sentence = null;
		String search = "No match found!";

		Runtime rt = Runtime.getRuntime();

	      // InmateInfoFrame inmateInfoFrame = new InmateInfoFrame();

	      JLabel textLabel = new JLabel();

	      textLabel.setBounds(50, 20, 100, 30);
		Process fp = rt.exec("python2 /usr/share/doc/python-fingerprint/examples/example_search.py");
		
	    BufferedReader stdInput = new BufferedReader(new InputStreamReader(fp.getInputStream()));
	    System.out.println("Here is the standard output of the command:\n");
	    while ((s = stdInput.readLine()) != null) {
	    	System.out.println(s);
	    	sentence = s + " ";
	    	//System.out.println(s.substring(s.lastIndexOf(":") + 1).replaceAll("\\s", ""));
	    }
	    
	    System.out.println(sentence+"ha");
	    if(sentence.toLowerCase().indexOf(search.toLowerCase()) != -1){
	    	
	    	textLabel.setText("Not found");
	        inmateIdFrame.add(textLabel);
	        inmateIdFrame.getContentPane().setBackground(Color.RED );
		      

	        display(inmateIdFrame);

	    }
	    else
	    {
	    	textLabel.setText("Found found found");
	        inmateIdFrame.add(textLabel);
	        inmateIdFrame.getContentPane().setBackground(Color.GREEN );

	        display(inmateIdFrame);
	    }
}
    public static void display(JFrame inmateIdFrame) throws IOException
    {
        inmateIdFrame.setSize(400, 400);
	    inmateIdFrame.setLayout(null);
	    inmateIdFrame.setLocationRelativeTo(null);
	    inmateIdFrame.setVisible(true);
    }

}
