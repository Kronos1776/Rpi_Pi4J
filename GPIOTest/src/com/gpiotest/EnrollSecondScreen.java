package com.gpiotest;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EnrollSecondScreen {


	EnrollSecondScreen(String empID, String siteID)
	{
		final String empId = empID;
		final String siteId = siteID;
		
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

				String fingerprint = "";
				String s;
				Runtime rt = Runtime.getRuntime();
				System.out.println("Waiting for you to scan your finger");
				Process fp;
				
				String search = "Template already exists at position";

				String searchdontmatch = "Fingers do not match";
				
				String searchenrolled = "Finger enrolled successfully!";
		    try
		    {				


					fp = rt.exec("python2 /usr/share/doc/python-fingerprint/examples/example_enroll.py");
					
	
			        BufferedReader stdInput = new BufferedReader(new InputStreamReader(fp.getInputStream()));
					
					while ((s = stdInput.readLine()) != null) 
					{
						System.out.println(s);
					    if(s.toLowerCase().indexOf(search.toLowerCase()) != -1){
					    		System.out.println("ALREADYYYYYYYY EXISTS");
					    		fingerprint = "";

							    sendFingerPrintDB(empId, siteId, fingerprint);
					    }
  				        else if (s.toLowerCase().indexOf(searchenrolled.toLowerCase()) != -1) //it doesnt take the only value of fp when entrolled too
  				        // only the fp which are new
					    {
						//gives the String for fingerprint
					    	if((s.contains(":")) )
					    	{
					        	fingerprint = s.substring(s.lastIndexOf(":") + 1);
					        	System.out.println("FINGERPRINTTT" + fingerprint);
							    sendFingerPrintDB(empId, siteId, fingerprint);
					    	}
					    }
					    else if (s.toLowerCase().indexOf(searchdontmatch.toLowerCase()) != -1) // only the fp which are new
					    {
						//gives the String for fingerprint
					    	
			        	fingerprint = "";
			        	System.out.println("FINGERPRINTTT" + fingerprint);
					    sendFingerPrintDB(empId, siteId, fingerprint);
					    }
					    else
					    {
					    	fingerprint = "";

						    sendFingerPrintDB(empId, siteId, fingerprint);
					    }
					}
					
			        BufferedReader stdError = new BufferedReader(new InputStreamReader(fp.getErrorStream()));
			        // read any errors from the attempted command
			        System.out.println("Here is the standard error of the command (if any):\n");
	
					while ((s = stdError.readLine()) != null) 
					{
						    System.out.println(s);

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
	      public static void sendFingerPrintDB(String empId, String siteId,String fingerprint)
	      {
	    	try {
				Connection con = null;
				Class.forName("com.mysql.jdbc.Driver");
				
				con = java.sql.DriverManager.getConnection("jdbc:mysql://10.131.25.142:3306/information_pi", "raspberry", "raspberrypi");
				
				
				java.sql.PreparedStatement insertfingerPrintPS = null;

				String insertfingerPrintSQL = "UPDATE information_pi.info_fingerprint " +
											  "SET " + 
											  "  fingerprint = ? " + 
											  "WHERE " + 
											  "  companyid = ? and " + 
											  "  siteid = ? ";

				
				insertfingerPrintPS = con.prepareStatement(insertfingerPrintSQL);

				insertfingerPrintPS.setString(1, fingerprint);
				insertfingerPrintPS.setString(2, empId);
				insertfingerPrintPS.setString(3, siteId);
				System.out.println(insertfingerPrintPS);

				insertfingerPrintPS.executeUpdate();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
