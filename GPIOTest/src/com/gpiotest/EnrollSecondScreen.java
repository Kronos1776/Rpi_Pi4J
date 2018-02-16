package com.gpiotest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EnrollSecondScreen {


	EnrollSecondScreen()
	{
	      JFrame inmateIdFrame = new JFrame("Enroll Fingerprint");
	      JButton yesBttn = new JButton("Yes");
	      yesBttn.setBounds(50, 150, 95, 30);
	      
	      JLabel enterIDLabel = new JLabel("Enroll fingerprint?");
	      
	      inmateIdFrame.add(enterIDLabel);
	      enterIDLabel.setBounds(50, 70, 200, 30);
	      inmateIdFrame.add(yesBttn);

	      inmateIdFrame.setSize(400, 400);
	      inmateIdFrame.setLayout(null);
	      inmateIdFrame.setVisible(true);
	      yesBttn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//to get a fingerprint from the user
				String s;
				Runtime rt = Runtime.getRuntime();

				System.out.println("Waiting for you to scan your finger");
				Process fp;
		    try
		    {
					fp = rt.exec("python2 /usr/share/doc/python-fingerprint/examples/example_enroll.py");
					
	
			        BufferedReader stdInput = new BufferedReader(new InputStreamReader(fp.getInputStream()));
					
					while ((s = stdInput.readLine()) != null) 
					{
						System.out.println(s);
						//gives the String for fingerprint
					    System.out.println(s.substring(s.lastIndexOf(":") + 1).replaceAll("\\s", ""));   
					}
					
			        BufferedReader stdError = new BufferedReader(new InputStreamReader(fp.getErrorStream()));
			        // read any errors from the attempted command
			        System.out.println("Here is the standard error of the command (if any):\n");
	
					while ((s = stdError.readLine()) != null) 
					{
						    System.out.println(s);
						    System.out.println(" here");
					}
			        System.exit(0);
		    }
		    catch (IOException e) 
		    {
				e.printStackTrace();
			}
			}
		});
	}
}
