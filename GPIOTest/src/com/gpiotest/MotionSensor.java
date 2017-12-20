
package com.gpiotest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;


/*
 * 
import com.hopding.jrpicam.RPiCamera;
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
 *                                          
 *                        _    
 *                      /| |\  
 *                      \| |/
 *                       |_|--------------- black wire (-) GROUND
 *                       | |
 *                       | +--------------- yellow wire GPIO21
 *                       |
 *                       +----------------- red wire (+) 5V
 *                                                
 */

public class MotionSensor {


	 public static void main(String[] args) throws Exception {
		 //

			//final RPiCamera piCam = new RPiCamera("/home/pi/Pictures");
			InputStreamReader keyboard = new InputStreamReader(System.in);
			char theInput = 'a';
			System.out.print("Starting the project\n");
			// GPIO Controller
			final GpioController gpioSensor = GpioFactory.getInstance();
			final GpioPinDigitalInput sensor = gpioSensor.provisionDigitalInputPin(RaspiPin.GPIO_29, PinPullResistance.PULL_DOWN);
		
			// GPIO Controller LED
			final GpioController gpioLED = GpioFactory.getInstance();		
			final GpioPinDigitalOutput led = gpioLED.provisionDigitalOutputPin(RaspiPin.GPIO_00);
			led.high();
			
			final Runtime rt = Runtime.getRuntime();


			sensor.addListener(new GpioPinListenerDigital() {

				@Override
				public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) 
				{
					try
					{
						Connection con = null;
						con = java.sql.DriverManager.getConnection("jdbc:mysql://10.131.25.142:3306/information_pi", "raspberry", "raspberrypi");
						
						System.out.println("success");
						if(event.getState().isHigh())
						{
							System.out.println("Motion detected");
							for( int i =1; i <=2; i++)
							{
								String dateTime = new SimpleDateFormat("MMddyyyyHHmmss").format(new Date());
								String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
								SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
								java.util.Date dateStr = formatter.parse(date);
								
								java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
								Process snap = rt.exec("raspistill -o " + dateTime + ".jpg --nopreview");
								java.sql.PreparedStatement insertInfoCamPS = null;
								String insertInfoCamSQL = "insert into info_camera " +
														" 	(picDate, " +
														" 	fileName) " +
														"values (?,?)";
								insertInfoCamPS = con.prepareStatement(insertInfoCamSQL);
								insertInfoCamPS.setDate(1, dateDB);
								insertInfoCamPS.setString(2, dateTime);
								insertInfoCamPS.executeUpdate();
														
								snap.waitFor();
							}
	
						}
						led.toggle();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
					System.out.println("snaps completed");
					if(event.getState().isLow()){
						System.out.println("All is Quiet :) ");
				         
						led.toggle();
					}
				}
			});
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
	}

} 