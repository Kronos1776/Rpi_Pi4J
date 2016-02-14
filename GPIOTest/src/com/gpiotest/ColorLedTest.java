package com.gpiotest;

import java.io.IOException;
import java.io.InputStreamReader;
//import java.text.DecimalFormat;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwm;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.impl.GpioPinImpl;

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
 *                                               
 *               ____________                  
 *              /            |                 
 *             |   Color     |-------      [4 Blue]---------- PWM0 (P4J-26)     some instructions recommended a 100 Ohm resistor (did not use here, was too dim)
 *             |             |---------    [3 Green]--------- PWM1 (P4J-24)     some instructions recommended a 100 Ohm resistor (did not use here, was too dim)      
 *             |   LED       |-------------[2 Ground]-------- GND  
 *             |             |---------    [1 Red]----------- PWM1 (P4J-01)     some instructions recommended a 180 Ohm resistor (did not use here, was too dim)
 *              \____________|                  
 *      
 *                            Longest lead is # 2 and is the ground.
 *                            
 *                            Reference: http://wiring.org.co/learning/basics/rgbled.html
 *                            
 *                            The PWM pins work in pairs: Channel 1 and Channel 0.  For this reason, the program is partially successful in
 *                            producing a variable color output.  To get best results, a PWM/servo driver card is needed.  The servo card can be
 *                            connected through the SDA/SLC pins.  
 *                            
 *                            Reference: https://learn.adafruit.com/adafruit-16-channel-servo-driver-with-raspberry-pi/hooking-it-up  
 *                            
 *                            Some sites recommend using a software PWM, but also recommend not using software PWM if consistent output is desired.  
 *                            
 *                            Long story short. Without a third PWM channel, two colors will be locked together (they BOTH get whatever value was set
 *                            on the channel last).
 *                            
 *                            Tags: Color, LED, RGB, PWM
 */

/**
 * This example code demonstrates how to use a 
 * color LED on the Raspberry Pi
 * using the Pi4J library.  
 */
public class ColorLedTest
{

   public static void main(String[] args) throws InterruptedException
   {
      InputStreamReader keyboard = new InputStreamReader(System.in);
      char theInput = 'a';
      double redValue = 0;
      double greenValue = 0;
      double blueValue = 0;
      // DecimalFormat df = new DecimalFormat("###0.00");

      System.out.println("<--Pi4J--> GPIO Color Example ... started.");

      // create gpio controller
      final GpioController gpio = GpioFactory.getInstance();

      // Setup pins to be used
      final GpioPinPwm redPin = gpio.provisionPwmOutputPin(RaspiPin.GPIO_01); // Pulse Width Modulation (PWM)
      final GpioPinPwm greenPin = gpio.provisionPwmOutputPin(RaspiPin.GPIO_24); // Pulse Width Modulation (PWM)
      final GpioPinPwm bluePin = gpio.provisionPwmOutputPin(RaspiPin.GPIO_26); // Pulse Width Modulation (PWM)

      // Use a pin implementation that allows setting the pin value
      final GpioPinImpl redPin_imp = new GpioPinImpl(gpio, GpioFactory.getDefaultProvider(), redPin.getPin());
      final GpioPinImpl greenPin_imp = new GpioPinImpl(gpio, GpioFactory.getDefaultProvider(), greenPin.getPin());
      final GpioPinImpl bluePin_imp = new GpioPinImpl(gpio, GpioFactory.getDefaultProvider(), bluePin.getPin());

      System.out.println(" ... the LED continue to change color until program has terminated.");
      System.out.println(" ... Type 'Q' and press [ENTER] to terminate.");

      // --- Keep program running until user aborts 'Q'
      do
      {
         try
         {
            if (keyboard.ready())
            {
               theInput = (char) keyboard.read();
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }

         // --- Get random values for the color strengths
         redValue = Math.random() * 100;
         greenValue = Math.random() * 100;
         blueValue = Math.random() * 100;

         // --- Set the color values
         redPin_imp.setPwm((int) redValue);
         greenPin_imp.setPwm((int) greenValue);
         bluePin_imp.setPwm((int) blueValue);

         // --- Print out color values that were sent
         // System.out.print("Red:" + df.format(redValue) + " ");
         // System.out.print("Green:" + df.format(greenValue) + " ");
         // System.out.println("Blue:" + df.format(blueValue));
         // --- Print out integer sent values... read pin values
         // System.out.println((int)redValue + " " + (int)greenValue + " " + (int)blueValue);
         // System.out.println(bluePin_imp.getPwm() + "-----" + redPin_imp.getPwm());

         Thread.sleep(500);

      } while (theInput != 'Q');

      // Turn off all colors
      redPin_imp.setPwm(0);
      greenPin_imp.setPwm(0);
      bluePin_imp.setPwm(0);

      // stop all GPIO activity/threads (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
      gpio.shutdown(); // <--- implement this method call if you wish to terminate the Pi4J GPIO controller
   }
}
