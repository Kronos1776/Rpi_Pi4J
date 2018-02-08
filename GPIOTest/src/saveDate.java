import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;


public class saveDate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try
		{
			Connection con = null;
			con = java.sql.DriverManager.getConnection("jdbc:mysql://10.131.25.142:3306/information_pi", "raspberry", "raspberrypi");
			String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			java.util.Date dateStr = formatter.parse(date);
			
			java.sql.Timestamp dateDB = new java.sql.Timestamp(dateStr.getTime());

			java.sql.PreparedStatement insertInfoCamPS = null;
			String insertInfoCamSQL = "insert into info_fingerprint " +
									" 	( companyid, " +
									"   name," +
									"   fingerprint," +
									"   date) " +
									"values (?,?,?,?)";
			insertInfoCamPS = con.prepareStatement(insertInfoCamSQL);
			insertInfoCamPS.setInt(1, 444);
			insertInfoCamPS.setString(2, "Neha");
			insertInfoCamPS.setString(3, "adsrjafljeswtrfiei0f2kl2j4x5d7z8asdadfccvadfadgfswedfgas5gfhxd6dgf6");
			insertInfoCamPS.setTimestamp(4, dateDB);

			insertInfoCamPS.executeUpdate();
				
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}

}
