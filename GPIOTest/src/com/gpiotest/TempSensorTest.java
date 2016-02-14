package com.gpiotest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

/*
 * Diagram is for Raspberry Pi 2 model B
 * Setup for this program:  BCM = Broadcom;   P4J = Pi4J (Java API)
 *                                                                                                                                            
 *       |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |
 *       |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |
 *       .      .      .      .      .      PWM0   .      .      .      .      .      .      .      .      .      PWM0   .      .      .      .
 *       .      .      .      ----UART----- PCM--- .      .      .      .      .      -----PCI----- I2C--- .      .      .      .      -----PCM----       
 *       .      .      .      TXD    RXD    CLK    .      .      .      .      .      CE0    CE1    SCL0   .      .      .      .      DIN    DOUT   
 * P4J   .      .      .      15     16     1      .      4      5      .      6      10     11     EEPROM .      26     .      27     28     29
 * BCM   5V     5V     GND    GPIO14 GPIO15 GPIO18 GND    GPIO23 GPIO24 GND    GPIO25 GPIO8  GPIO7  IDSC   GND    GPIO12 GND    GPIO16 GPIO20 GPIO21
 * Pin # 2      4      6      8      10     12     14     16     18     20     22     24     26     28     30     32     34     36     38     40
 *       --------------------------------------------------------------------------------------------------------------------------------------------
 * Pin # 1      3      5      7      9      11     13     15     17     19     21     23     25     27     29     31     33     35     37     39
 * BCM   3.3V   GPIO2  GPIO3  GPIO4  GND    GPIO17 GPIO27 GPIO22 3.3V   GPIO10 GPIO9  GPIO11 GND    IDSD   GPIO5  GPIO6  GPIO13 GPIO19 GPIO26 GND
 * P4J   .      8      9      7      .      0      2      3      .      12     13     14     .      EEPROM 21     22     23     24     25     .
 *       .      SDA1   SCL1   .      .      .      .      .      .      MOSI   MISO   SCLK   .      SDA0   GPCLK1 GPCLK2 .      FS     .      .
 *       .      -----I2C----- .      .      .      .      .      .      ---------SPI-------- .      I2C--- .      .      .      PCM--- .      .
 *       .      .      .      .      .      .      .      .      .      .      .      .      .      .      .      .      PWM1   PWM1   .      .
 *       |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |
 *       |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |
 *                            ^                                  ^                           ^                                          
 *                            A----------------------------+     B----------------------+    C--------------+
 *                                                         |                            |                   |
 *                                                    _    | yellow wire                |                   |
 *                                 temperature probe | |   |                            |                   |
 *                                                   | |   |       10k resistor         |                   |
 *                                                   |_|A--+-----^v^v^v^v^v^-----+      |                   |
 *                                                   | |                         |      |                   |
 *                                                   | +B------------------------+------+ red wire (-) 3.3V |
 *                                                   |                                                      |
 *                                                   +C-----------------------------------------------------+ blue wire (+) GROUND
 *                  
 *                                                
 *  1. Make sure that /boot/config.txt has the following entry in it (only needs to be done if first time):
 *  
 *     # w1-gpio (Using this for temp sensor)
 *     overlay=w1-gpio,gpiopin=4
 *  
 *  2. Reboot (if first time)
 *  
 *  3. Execute the following commands:
 *  
 *     sudo modprobe w1-gpio
 *     sudo modprobe w1-therm
 *     
 *  4. Should have a directory that starts with 28-xxxx in /sys/bus/w1/devices.  Inside that directory
 *     is a file named w1_slave. cat-ing the file shows the raw reading from the probe.  The temperature
 *     is on line 2
 *                                                
 */
public class TempSensorTest 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		BufferedReader tempSensorDevice_Slave = null;
		int readCount = 0;
		String[] temperatureData = new String[2];
		double temperature = 0;
		double temperatureF = 0;
		DecimalFormat df = new DecimalFormat("#,##0.00");

		try {
			tempSensorDevice_Slave = new BufferedReader(new FileReader("/sys/bus/w1/devices/28-0000075c717b/w1_slave"));
			
			while(tempSensorDevice_Slave.ready() && readCount < 2)
			{
				temperatureData[readCount] = tempSensorDevice_Slave.readLine();
				readCount++;
			}
			
			// Lines read in are similar to this
			// 35 02 4b 46 7f ff 0b 10 76 : crc=76 YES
			// 35 02 4b 46 7f ff 0b 10 76 t=35312
			
			temperature = Double.parseDouble(temperatureData[1].substring(temperatureData[1].indexOf("=") + 1));
			temperature = temperature / 1000;
			temperatureF = ((temperature * 9) / 5) + 32;
			System.out.println("Raw Reading: " + temperatureData[1]);
			System.out.println("Temperature in Celcius: " + df.format(temperature));
			System.out.println("Temperature in Fahrenheit: " + df.format(temperatureF));
		} 
		catch (FileNotFoundException e) 
		{
			
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
