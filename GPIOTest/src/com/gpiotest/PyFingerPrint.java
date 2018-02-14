package com.gpiotest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class PyFingerPrint {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
	      String s = null;
	      String search = "SHA";
	      String notFound = "NO";
	     // final GpioController gpioLED = GpioFactory.getInstance();		
		 // final GpioPinDigitalOutput led = gpioLED.provisionDigitalOutputPin(RaspiPin.GPIO_00); //pin 17 to green led
		  
	      final GpioController gpioLED1 = GpioFactory.getInstance();		
		  final GpioPinDigitalOutput ledRed = gpioLED1.provisionDigitalOutputPin(RaspiPin.GPIO_02);
		  
		Runtime rt = Runtime.getRuntime();
		try
		{
			System.out.println("Waiting for you to scan your finger");
			Process fp = rt.exec("python2 /usr/share/doc/python-fingerprint/examples/example_search.py");
	
	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(fp.getInputStream()));
	    	
	        // read the output from the command
	        System.out.println("Here is the standard output of the command:\n");
	        while ((s = stdInput.readLine()) != null) {
	        	System.out.println(s);
	        	// no led on/off required as added a line to python code
/*	            if(s.toLowerCase().indexOf(search.toLowerCase())!= -1)
	            {
	            	System.out.println("Did");
	    			led.high();
	    			Thread.sleep(1000);
	    			led.low();
	            }
	            if(s.toLowerCase().contains(notFound.toLowerCase()))
	            {
	            	System.out.println("Didnott");
	    			ledRed.high();
	    			Thread.sleep(1000);
	    			ledRed.low();
	            }*/
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
    catch (IOException e) {
        System.out.println("exception happened - here's what I know: ");
        e.printStackTrace();
        System.exit(-1);
    }
}
}

