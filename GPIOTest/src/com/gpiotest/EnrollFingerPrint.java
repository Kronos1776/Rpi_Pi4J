package com.gpiotest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class EnrollFingerPrint {

	public static void main(String[] args)
	{

   	        JFrame inmateIdFrame = new JFrame("Button Example");


			String empName = null;
			String empDate = null;
			String fingerPrint = null;
			Scanner in = new Scanner(System.in);


		      String [] siteOptions = { "COR" , "TCA" , "TFA" , "OTR" };
		      final JComboBox siteList = new JComboBox(siteOptions);
		      siteList.setBounds(50, 20, 200, 31);		      
		      
		      JLabel enterIDLabel = new JLabel("Enter ID number:");
		      enterIDLabel.setBounds(50, 70, 100, 30);
		      
/*		      
		      
			System.out.println("Enter your Employee name");
			empName = in.nextLine();
			System.out.println("This is what you entered "+empName);
			
			System.out.println("Enter your Employee number");
			empId = in.nextInt();
			System.out.println("This is what you entered "+empId);*/
		      
		      final JTextField inmateidField = new JTextField();
		      inmateidField.setBounds(50, 100, 200, 20);

		      JButton bttn = new JButton("Click Here");
		      bttn.setBounds(50, 150, 95, 30);
		      
			   

		/*	
			System.out.println("Enter the Date");
			empDate = in.nextLine();*/
	bttn.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			try 
			{
	
				String empId;
				String siteId;
	
				Connection con = null;
				Class.forName("com.mysql.jdbc.Driver");
	
				con = java.sql.DriverManager.getConnection("jdbc:mysql://10.131.25.142:3306/information_pi", "raspberry", "raspberrypi");
	
				empId = inmateidField.getText();
				siteId = (String) siteList.getSelectedItem();
	
				java.sql.PreparedStatement selecttheidPS = null;
				java.sql.ResultSet selecttheidRS = null;
				String selecttheidSQL = "select " + 
										"  companyid, " +
										"  fingerprint , " + 
										"  empname, " + 
										"  siteid " +
										"from " +
										"  information_pi.info_fingerprint " + 
										"where " + 
										"  companyid = ? and " + 
										"  siteid = ? ";
	
				selecttheidPS = con.prepareStatement(selecttheidSQL);
	
				selecttheidPS.setString(1, empId);
				selecttheidPS.setString(2, siteId);
				System.out.println(selecttheidPS);
	
				selecttheidRS = selecttheidPS.executeQuery();
	
				if (selecttheidRS.next()) {

					System.out.println("Here "+selecttheidRS.getString("fingerprint")+" are");
					
					if ( empId.equals(selecttheidRS.getString("companyid"))&&siteId.equals(selecttheidRS.getString("siteid")))
					{
						System.out.println("we are here");
						if(selecttheidRS.getString("fingerprint").equals(""))
						{
							System.out.println("Important");
							EnrollSecondScreen enrollF = new EnrollSecondScreen();
						}
					}
/*					do {
						if ((siteId.equals(selecttheidRS.getString("siteid")) && empId.equals(selecttheidRS
								.getString("companyid")))){
							System.out.println("we are here");
						}
						// fingerPrint = selecttheidRS.getString("fingerprint");
						// empId = selecttheidRS.getInt("companyid");
						System.out.println(empId + "here");
					} while (selecttheidRS.next());*/
				} 
				else 
				{
					System.out.println("Employee not found");
				}
	
			} 
			catch (SQLException e) 
			{
	
			} catch (ClassNotFoundException ee) 
			{
	
			}
			}
		});
    inmateIdFrame.add(enterIDLabel);

    inmateIdFrame.add(bttn);
    inmateIdFrame.add(inmateidField);
    inmateIdFrame.add(siteList);
    inmateIdFrame.setSize(400, 400);
    inmateIdFrame.setLayout(null);
    inmateIdFrame.setVisible(true);
		}
	

}

			
	