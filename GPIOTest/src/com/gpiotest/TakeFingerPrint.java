package com.gpiotest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TakeFingerPrint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try
		{
			String s;
		      JFrame inmateIdFrame = new JFrame("Button Example");
		      // InmateInfoFrame inmateInfoFrame = new InmateInfoFrame();

		      String [] siteOptions = { "TCF" , "TCA" , "COR" , "OTR" };
		      final JComboBox siteList = new JComboBox(siteOptions);
		      siteList.setBounds(50, 20, 200, 31);
		      
		      JLabel enterIDLabel = new JLabel("Enter ID number:");
		      enterIDLabel.setBounds(50, 70, 100, 30);
		      
		      
		      final JTextField inmateidField = new JTextField();
		      inmateidField.setBounds(50, 100, 200, 20);

		      JButton bttn = new JButton("Click Here");
		      bttn.setBounds(50, 150, 95, 30);
		      
		      
		      String empId= "";
		      
		      String empName = "";
		      
		      empId = inmateidField.getText();
		      String siteId = (String) siteList.getSelectedItem();
		   
		         inmateIdFrame.add(enterIDLabel);

		         inmateIdFrame.add(bttn);
		         inmateIdFrame.add(inmateidField);
		         inmateIdFrame.add(siteList);
		         inmateIdFrame.setSize(400, 400);
		         inmateIdFrame.setLayout(null);
		         inmateIdFrame.setVisible(true);
		         new CheckFingerPrint();
	      }
	      catch (NumberFormatException e1)
	      {
	         System.out.println("Wahoo");
	         System.out.println(e1);
	      }
	      catch (Exception e)
	      {
	         System.out.println("Nooooo!");
	         System.out.println("error" + e);
	      }
	}
}

