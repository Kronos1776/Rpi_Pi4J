package com.gpiotest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class toCheckTheFingerPrint {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {

		Connection con = null;
		int empId = 0;
		String empName = null;
		String empDate = null;
		String fingerPrint = null;
		Scanner in = new Scanner(System.in);
		String s;
		con = java.sql.DriverManager.getConnection("jdbc:mysql://10.131.25.142:3306/information_pi", "raspberry", "raspberrypi");

		Runtime rt = Runtime.getRuntime();
	try
	{
		System.out.println("Enter your Employee name");
		empName = in.nextLine();
		System.out.println("This is what you entered "+empName);
		
		System.out.println("Enter your Employee number");
		empId = in.nextInt();
		System.out.println("This is what you entered "+empId);
		
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
		
		selecttheidRS = selecttheidPS.executeQuery();

		if (selecttheidRS.next())
		{
			do
			{
				if((empName.equals(selecttheidRS.getString("empname")) && empId == selecttheidRS.getInt("companyid") ))
				{
					System.out.println("matched");

					System.out.println("Please scan your finger");
					Process fp = rt.exec("python2 /usr/share/doc/python-fingerprint/examples/example_search.py");
			        BufferedReader stdInput = new BufferedReader(new InputStreamReader(fp.getInputStream()));
			    	
			        // read the output from the command
			        System.out.println("Here is the standard output of the command:\n");
			        while ((s = stdInput.readLine()) != null) {
			        	System.out.println(s); // Complete string
			            fingerPrint = s.substring(s.lastIndexOf(":") + 1).replaceAll("\\s", ""); //gives just the value for the fingerprint
			        }
			        BufferedReader stdError = new BufferedReader(new InputStreamReader(fp.getErrorStream()));
			        // read any errors from the attempted command
			        System.out.println("Here is the standard error of the command (if any):\n");
			        while ((s = stdError.readLine()) != null) {
			            System.out.println(s);
			            System.out.println(" here");
			        }
			        // if and else to check if the finger print matches
		            if(fingerPrint.equals(selecttheidRS.getString("fingerprint")))
		            {
		            	System.out.println("Door opens");
		            }
		            else
		            {
		            	System.out.println("aren't authorized");
		            }
			    }
				
				fingerPrint = selecttheidRS.getString("fingerprint");
				empId = selecttheidRS.getInt("companyid");

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
			System.out.println(e.getMessage());
		}

    System.exit(0);
}
}