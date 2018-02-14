package com.gpiotest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pi4j.system.SystemInfo.BoardType;

public class FinalScreen {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
		      JFrame inmateIdFrame = new JFrame("Button Example");
		      
		      JButton bttnContinue = new JButton("Continue");
		      bttnContinue.setBounds(10, 100, 95, 30);
		      JButton bttnEnroll = new JButton("Enroll");
		      bttnEnroll.setBounds(110, 100, 95, 30);
		      JButton bttnCancel = new JButton("Cancel");
		      bttnCancel.setBounds(210, 100, 95, 30); 
		      
		      bttnContinue.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {

				     JFrame inmateIdFrame = new JFrame("Button Example");
				     try {
						search(inmateIdFrame);
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
		      
 		      bttnEnroll.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {

				     JFrame inmateIdFrame = new JFrame("Button Example");
					    
				     try {
						enroll(inmateIdFrame);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			});
/*		      bttnCancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Runtime rt = Runtime.getRuntime();

				      // InmateInfoFrame inmateInfoFrame = new InmateInfoFrame();

				      JLabel textLabel = new JLabel();

				      textLabel.setBounds(50, 20, 100, 30);
					try {
						Process fp = rt.exec("sys.exit");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
		*/

	         inmateIdFrame.add(bttnContinue);
	         inmateIdFrame.add(bttnEnroll);
	         inmateIdFrame.add(bttnCancel);    
	         inmateIdFrame.setSize(400, 400);
		     inmateIdFrame.setLayout(null);
		     inmateIdFrame.setVisible(true);	
		     
		}
		catch(NullPointerException e1)
		{
			System.out.println("NullPointerException");
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
    public static void enroll(JFrame inmateIdFrame) throws IOException
	{
		String s = null;
		String sentence = null;
		String search = "Template already exists at position";
		String searchEnrolled = "New template position";

		Runtime rtEnroll = Runtime.getRuntime();

	      // InmateInfoFrame inmateInfoFrame = new InmateInfoFrame();

	      JLabel textLabel = new JLabel();

	    textLabel.setBounds(50, 20, 100, 30);
		Process fp = rtEnroll.exec("python2 /usr/share/doc/python-fingerprint/examples/example_enroll.py");
		
	    BufferedReader stdInput = new BufferedReader(new InputStreamReader(fp.getInputStream()));
	    System.out.println("Here is the standard output of the command:\n");
	    while ((s = stdInput.readLine()) != null) {
	    	System.out.println(s);
	    	sentence = s + " ";
	    	//System.out.println(s.substring(s.lastIndexOf(":") + 1).replaceAll("\\s", ""));
	}
	    if(sentence.toLowerCase().indexOf(searchEnrolled.toLowerCase()) != -1)
	    {  //if nothing matched
	    	System.out.println("HEreeeee");
	    	textLabel.setText("Enrolled!!!!");
	        inmateIdFrame.add(textLabel);
	        inmateIdFrame.getContentPane().setBackground( Color.GREEN );
	        display(inmateIdFrame);

	    }
	    else if(sentence.toLowerCase().indexOf(search.toLowerCase()) != -1)
	    {
	    	textLabel.setText("Already exists!");
	        inmateIdFrame.add(textLabel);
	        inmateIdFrame.getContentPane().setBackground( Color.YELLOW );
	        display(inmateIdFrame);
	    }
	    else
	    {
	    	textLabel.setText("Fingers do not match");
	        inmateIdFrame.add(textLabel);
	        inmateIdFrame.getContentPane().setBackground( Color.RED );
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
