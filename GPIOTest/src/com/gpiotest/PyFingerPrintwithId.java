package com.gpiotest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class PyFingerPrintwithId {

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {


		try
		{

			Connection con = null;
			int empId = 0;
			String empName = null;
			String empDate = null;
			String fingerPrint = null;
			Scanner in = new Scanner(System.in);
			
			con = java.sql.DriverManager.getConnection("jdbc:mysql://10.131.25.142:3306/information_pi", "raspberry", "raspberrypi");

			System.out.println("Enter your Employee name");
			empName = in.nextLine();
			System.out.println("This is what you entered "+empName);
			
			System.out.println("Enter your Employee number");
			empId = in.nextInt();
			System.out.println("This is what you entered "+empId);
			
		/*	
			System.out.println("Enter the Date");
			empDate = in.nextLine();*/
			
			java.sql.PreparedStatement selecttheidPS = null;
			java.sql.ResultSet selecttheidRS = null;
			String selecttheidSQL = "select " +
									"  companyid, " +
					                "  fingerprint, " +
					                "  empname " +
									"from " +
					                "  information_pi.info_fingerprint " +
									"where " +
					                "  companyid = ? and " +
									"  empname = ? ";
			
			selecttheidPS = con.prepareStatement(selecttheidSQL);
			
			selecttheidPS.setInt(1, empId);
			selecttheidPS.setString(2, empName);
			System.out.println(selecttheidPS);
			
			selecttheidRS = selecttheidPS.executeQuery();

			if (selecttheidRS.next())
			{
			   System.out.println(selecttheidRS.getString("companyid")+"no way");
				do
				{
					if((empName.equals(selecttheidRS.getString("empname")) && empId == selecttheidRS.getInt("companyid") ))
					{
						System.out.println("we are here");
					}
					fingerPrint = selecttheidRS.getString("fingerprint");
					empId = selecttheidRS.getInt("companyid");
					System.out.println(empId+"here");
				} while(selecttheidRS.next());
			}
			else
			{
				System.out.println("Employee not found");
			}
		
			selecttheidPS.close();
		}
		catch(Exception e)
		{
	
		}
}
}