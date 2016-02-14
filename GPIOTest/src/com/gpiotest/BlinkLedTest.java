package com.gpiotest;

import java.io.IOException;
import java.io.InputStreamReader;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

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
public class BlinkLedTest 
{
    
    public static void main(String[] args) throws InterruptedException 
    {
    	InputStreamReader keyboard = new InputStreamReader(System.in);
        char theInput = 'a';
        
        System.out.println("<--Pi4J--> GPIO Blink Example ... started.");
        
        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();
        
        final GpioPinDigitalOutput led00 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00); // GPIO 17 (11)

        // continuously blink the led every 1/2 second for 15 seconds
//        led1.blink(500, 15000);
//
//        // continuously blink the led every 1 second 
//        led2.blink(1000);
        
        System.out.println(" ... the LED will continue blinking until the program is terminated.");
        System.out.println(" ... Type 'Q' and press [ENTER] to terminate.");
        
        // keep program running until user aborts (CTRL-C)
        do 
        {
        	try 
        	{
        		if (keyboard.ready())
        		{
				   theInput = (char)keyboard.read();
        		}
			} 
        	catch (IOException e) 
			{
				e.printStackTrace();
			}
        	
            Thread.sleep(1000);
            led00.toggle();
        } while (theInput !='Q');
        
        // stop all GPIO activity/threads
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        led00.setState(false);
        gpio.shutdown();   // <--- implement this method call if you wish to terminate the Pi4J GPIO controller
    }
}
