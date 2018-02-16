package com.gpiotest;

import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.omg.PortableInterceptor.INACTIVE;

public class InmateInfoKey
{

   public static void main(String[] args)
   {
      // InmateInfoKey inmateInfoKey = new InmateInfoKey();
      JFrame inmateIdFrame = new JFrame("Button Example");
      // InmateInfoFrame inmateInfoFrame = new InmateInfoFrame();

      String [] siteOptions = { "TCF" , "TCA" , "COR" , "OTR" };
      final JComboBox siteList = new JComboBox(siteOptions);
      siteList.setBounds(50, 20, 200, 31);
      
      JLabel enterIDLabel = new JLabel("Enter ID number:");
      enterIDLabel.setBounds(50, 70, 100, 30);
      
      
      final JTextField inmateidField = new JTextField();
      inmateidField.setBounds(50, 100, 200, 20);

      JButton bttn = new JButton("Click Here");
      bttn.setBounds(50, 150, 95, 30);
      try
      {
         bttn.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
             //  String value = tf.getText();

        	   	String inmateId = inmateidField.getText();
        	   	String siteId = (String) siteList.getSelectedItem();
                Connection con = null;

               try
               {
                     Class.forName("com.mysql.jdbc.Driver");
                     con = DriverManager.getConnection("jdbc:mysql://10.131.1.133:3306/ims?zeroDateTimeBehavior=convertToNull&useSSL=true&verifyServerCertificate=false&requireSSL=true", "ims", "ims");

                     PreparedStatement selectInmateImagePS = null;
                     java.sql.ResultSet selectInmateImageRS = null;
                     String selectInmateImageSQL = "select  " +
                           "  i.inmateid,  " +
                           "  i.firstname,  " +
                           "  i.lastname, " +
                           "  im.image, " +
                           "  im.arrivalDate " +
                           "from  " +
                           "  ims.iminmate i " +
                           "  inner join ims.images im " +
                           "    on  " +
                           "    i.inmateid = im.inmateid and " +
                           "    i.siteid = im.siteid and " +
                           "    i.arrivalDate = im.arrivalDate " +
                           "where  " +
                           "  i.siteid= ? and  " +
                           "  i.inmateid= ? and " +
                           "  i.departureDate is null and " +
                           "  im.primaryImage = 1 ";

                     String firstname = null;
                     String lastname = null;
                     String inmateid = null;

                     selectInmateImagePS = con.prepareStatement(selectInmateImageSQL);

                     selectInmateImagePS.setString(1, siteId);
                     selectInmateImagePS.setString(2, inmateId);
                     
                     selectInmateImageRS = selectInmateImagePS.executeQuery();
                     
                     if (selectInmateImageRS.next())
                     {
                        java.sql.Blob blob = null;
                        // blob = selectInmateImageRS.getBlob("im.primaryimage");
                        firstname = selectInmateImageRS.getString("i.firstname");
                        lastname = selectInmateImageRS.getString("i.lastname");
                        inmateid = selectInmateImageRS.getString("i.inmateid");

                        blob = selectInmateImageRS.getBlob("im.image");
                      
                        new InmateInfoFrame(firstname, lastname, inmateid, blob);
                        //new CheckFingerPrint();
                     }
                     else
                     {

                    	      System.out.println("Data not found");

                     }
               }
               catch (NumberFormatException numE)
               {
                  System.out.println(" numbers only");
               }
               catch (IOException e1)
               {

                  e1.printStackTrace();
               }
               catch (SQLException er)
               {
                  er.printStackTrace(System.out);
               }
               catch (ClassNotFoundException e1)
               {
                  e1.printStackTrace();
               }
               finally
               {
                  if (con != null)
                  {
                     try
                     {
                        con.close();
                     }
                     catch (SQLException exx)
                     {
                        exx.printStackTrace();
                     }
                  }
               }

            }
         });

         inmateIdFrame.add(enterIDLabel);

         inmateIdFrame.add(bttn);
         inmateIdFrame.add(inmateidField);
         inmateIdFrame.add(siteList);
         inmateIdFrame.setSize(400, 400);
         inmateIdFrame.setLayout(null);
         inmateIdFrame.setVisible(true);
         new CheckFingerPrint();
      }
      catch (NumberFormatException e1)
      {
         System.out.println("Wahoo");
         System.out.println(e1);
      }
      catch (Exception e)
      {
         System.out.println("Nooooo!");
         System.out.println("error" + e);
      }
   }
}