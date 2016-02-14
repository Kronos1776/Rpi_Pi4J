package com.gpiotest;

import java.io.IOException;
import java.io.InputStreamReader;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

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
 *                                          ^
 *                                          A--------------+
 *                                                         |
 *                               _                    _    | yellow wire
 *                              | |\                /| |   |
 *                              | |/~ ~ ~ ~ ~ ~ ~ ~ \| |   |
 *                              |_|                  |_|A<-+
 *                              | |                  | |
 *         3.3V red wire (-) ---+ |                  | +------------- red wire (-) 3.3V
 *                                |                  |
 *     GROUND black wire (+) -----+                  +--------------- black wire (+) GROUND
 *                                                
 */
public class BreakBeamTest
{

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      InputStreamReader keyboard = new InputStreamReader(System.in);
      char theInput = 'a';

      System.out.println("Break Beam Test Started");

      final GpioController gpioController = GpioFactory.getInstance();

      final GpioPinDigitalInput mySensor = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP); // GPIO 17 (P4J 0)

      // Setup listener for sensor events
      mySensor.addListener(new GpioPinListenerDigital()
      {

         @Override
         public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent theEvent)
         {
            System.out.println("Sensor " + theEvent.getPin().getName() + " - " + theEvent.getState().getValue());
         }
      });

      // Use a loop to keep program alive and checking for keyboard input ('Q' to terminate)
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

         try
         {
            Thread.sleep(500);
         }
         catch (InterruptedException e)
         {
            e.printStackTrace();
         }
      } while (theInput != 'Q');

      gpioController.shutdown();
   }

}
