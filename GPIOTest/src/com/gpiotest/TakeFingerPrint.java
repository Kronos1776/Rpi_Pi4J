package com.gpiotest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TakeFingerPrint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try
		{

			String s;

			//to get a fingerprint from the user
			Runtime rt = Runtime.getRuntime();

			System.out.println("Waiting for you to scan your finger");
			Process fp = rt.exec("python2 /usr/share/doc/python-fingerprint/examples/example_enroll.py");

	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(fp.getInputStream()));
			
	        while ((s = stdInput.readLine()) != null) {
	        	System.out.println(s);
	        	//gives the String for fingerprint
	            System.out.println(s.substring(s.lastIndexOf(":") + 1).replaceAll("\\s", ""));
	           
	        }
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(fp.getErrorStream()));
	        // read any errors from the attempted command
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((s = stdError.readLine()) != null) {
	            System.out.println(s);
	            System.out.println(" here");
	        }
	        System.exit(0);

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
