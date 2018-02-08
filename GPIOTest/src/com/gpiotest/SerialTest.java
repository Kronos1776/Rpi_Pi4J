package com.gpiotest;

import java.io.IOException;
import java.io.InputStreamReader;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.exception.UnsupportedBoardType;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.SerialPort;
import com.pi4j.io.serial.StopBits;
import com.pi4j.util.Console;

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
 *       ----------------------------------------------------------CopyOfBlinkLedTest----------------------------------------------------------------------------------
 * Pin # 1      3      5      7      9      11     13     15     17     19     21     23     25     27     29     31     33     35     37     39
 * BCM   3.3V   GPIO2  GPIO3  GPIO4  GND    GPIO17 GPIO27 GPIO22 3.3V   GPIO10 GPIO9  GPIO11 GND    IDSD   GPIO5  GPIO6  GPIO13 GPIO19 GPIO26 GND
 * P4J   .      8      9      7      .      0      2      3      .      12     13     14     .      EEPROM 21     22     23     24     25     .
 *       .      SDA1   SCL1   .      .      .      .      .      .      MOSI   MISO   SCLK   .      SDA0   GPCLK1 GPCLK2 .      FS     .      .
 *       .      -----I2C----- .      .      .      .      .      .      ---------SPI-------- .      I2C--- .      .      .      PCM--- .      .
 *       .      .      .      .      .      .      .      .      .      .      .      .      .      .      .      .      PWM1   PWM1   .      .
 *       |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |
 *       |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |
 *                                   B      A    
 *                                   ^      ^ 
 *                                 +-+      +-+
 *                                 |    __    |
 *                                 |   /  \   |
 *                                 |   |  |   +--+
 *                                 |   |__|      < resistor
 *                                 |   |  |led   >
 *                                 |   |  |      <
 *                                 |   |  |      >
 *                                 |   B  |   +--+
 *                                 |   ^  A   |
 *                                 |   |  ^   |
 *                                 +---+  +---+
 */

/**
 * This example code demonstrates how to perform simple 
 * blinking LED logic of a GPIO pin on the Raspberry Pi
 * using the Pi4J library.  
 */
public class SerialTest
{

   public static void main(String[] args) throws InterruptedException
   {
	   // to check if serial port is initialized

	   
	
      final Serial serial = SerialFactory.createInstance();
      
      serial.addListener(new SerialDataEventListener() {
		
		@Override
		public void dataReceived(SerialDataEvent arg0) {
			System.out.println("Received Data");
		}
	});
      
    try 
    {
    	System.out.println("Processing Started");
    	
		SerialConfig config = new SerialConfig();
		config.device(SerialPort.getDefaultPort())
		      .baud(Baud._38400)
		      .dataBits(DataBits._8)
		      .parity(Parity.NONE)
		      .stopBits(StopBits._1)
		      .flowControl(FlowControl.NONE);
		
		serial.open(config);
		
		serial.write("Hello World");
		
		serial.close();
		
		System.out.println("Processing Complete");
	} catch (UnsupportedBoardType e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalStateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}
