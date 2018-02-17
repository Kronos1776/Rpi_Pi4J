package com.gpiotest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class getDirectFromFP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection con = null;
		int empId = 0;
		String empName = null;
		String s = null;
		String fingerPrint = null;
		Scanner in = new Scanner(System.in);
		//Database connection
		try
		{
		con = java.sql.DriverManager.getConnection("jdbc:mysql://10.131.25.142:3306/information_pi", "raspberry", "raspberrypi");

		//to get a fingerprint from the user
		Runtime rt = Runtime.getRuntime();

		System.out.println("Waiting for you to scan your finger");
		Process fp = rt.exec("python2 /usr/share/doc/python-fingerprint/examples/example_enroll.py");

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(fp.getInputStream()));
        while ((s = stdInput.readLine()) != null) {
        	System.out.println(s);
            fingerPrint = s.substring(s.lastIndexOf(":") + 1);
            System.out.println(fingerPrint);
        }
        BufferedReader stdError = new BufferedReader(new InputStreamReader(fp.getErrorStream()));
        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
            System.out.println(" here");
        }
       /* if(!fingerPrint.equals("Nomatchfound!"))
        {
			java.sql.PreparedStatement insertthedataPS = null;
			System.out.println("here1");
			String insertthedataSQL = "insert into information_pi.info_fingerprint " +
									  "  (companyid, " +
					                  "  empname, " +
					                  "  fingerprint, " +
					                  "  date " +
					                  "  ) " +
									  "values (?,?,?,?)";

			insertthedataPS = con.prepareStatement(insertthedataSQL);
			insertthedataPS.setInt(1, empId);
			insertthedataPS.setString(2, empName);
			insertthedataPS.setString(3, fingerPrint);
			insertthedataPS.setTimestamp(4, dateDB);

			System.out.println(insertthedataPS);
			insertthedataPS.executeUpdate();
        }
        else
        {
        	System.out.println("Please try again");
        }*/
        System.exit(0);

	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
	}
}

	}
