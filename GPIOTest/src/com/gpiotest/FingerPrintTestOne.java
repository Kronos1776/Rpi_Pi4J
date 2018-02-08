package com.gpiotest;


import com.pi4j.wiringpi.Serial;

public class FingerPrintTestOne {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int serialPort = Serial.serialOpen(Serial.DEFAULT_COM_PORT, 57600);
		if(serialPort == -1)
		{
			System.out.println("not initialized");
		}
		else 
		{
			System.out.println("yes");
		}
		Serial.serialPuts(serialPort, "yo yo!!");
	while(true)
	{
		if(Serial.serialDataAvail(serialPort)>0)
		{
			char inchar  = (char)Serial.serialGetchar(serialPort);
			System.out.println(inchar);
		}
	}
  }

}
