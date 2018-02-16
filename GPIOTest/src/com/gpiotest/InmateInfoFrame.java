package com.gpiotest;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.runner.RunWith;


public class InmateInfoFrame extends JFrame
{
 //  JLabel          jb;
 //  BufferedImage   img;
 //  InmateInfoFrame infoFrame;

   InmateInfoFrame()
   {

   }

   InmateInfoFrame(String firstname, String lastname, String inmateid, java.sql.Blob blob) throws SQLException, IOException
   {
    //  infoFrame = new InmateInfoFrame();
      JFrame inmateInfoFrame = new JFrame("Image screen");
      JLabel inmateInfo = new JLabel();
      JLabel fingerprintscreen = new JLabel("Please place your finger on the fingerprint sensor");
/*      byte[] imageBytes = blob.getBytes(1, (int) blob.length());
      System.out.println("Here we are" +imageBytes);
      Image img = Toolkit.getDefaultToolkit().createImage(imageBytes);
      ImageIcon icon = new ImageIcon(img);

      JLabel lPhoto = new JLabel(new ImageIcon(img));
      lPhoto.setIcon(icon);
      lPhoto.setSize(600, 600);
      inmateImg.add(lPhoto);*/


    ////  InputStream binaryStream = blob.getBinaryStream(1, blob.length());
    ////  BufferedImage bfimg = null;
      
    ////  bfimg = ImageIO.read(binaryStream);
    //  Image img = bfimg; // no need of this
   
    	 
    
 ////   ImageIcon icon = new ImageIcon(bfimg.getScaledInstance(118,142,Image.SCALE_SMOOTH));
   //// inmateInfo.setIcon(icon);
  ////  inmateInfo.setFont (inmateInfo.getFont ().deriveFont (18.0f)); // change the size of inmate id, firstname and lastname
    
      inmateInfo.setText("<html>Inmate ID: " + inmateid + "<br>First name: " + firstname + "<br>Last name: " + lastname +"<br><br>Please place your finger on the fingerprint sensor"+"</html>");
      inmateInfo.setSize(400, 400);
    //  String str = "<html>Inmate ID: " + inmateid + "<br>First name: " + firstname + "<br>Last name: " + lastname +blob+"</html>";

    //  JLabel textLabel = new JLabel(str);
  
     // textLabel.setSize(200, 200);

    //  inmateInfoFrame.add(textLabel);

      inmateInfoFrame.add(inmateInfo);
      inmateInfoFrame.add(fingerprintscreen);
      inmateInfoFrame.setSize(400, 400);

      inmateInfoFrame.setLayout(null);
      inmateInfoFrame.setVisible(true);
     
   }

  }
