
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class AddTheDetailsToDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try
		{
			Connection con = null;
			int empId = 0;
			String empName = null;
			String s = null;
			String fingerPrint = null;
			Scanner in = new Scanner(System.in);
			//Database connection
			con = java.sql.DriverManager.getConnection("jdbc:mysql://10.131.25.142:3306/information_pi", "raspberry", "raspberrypi");
			//Get the data from the user
			System.out.println("Enter your Employee name");
			empName = in.nextLine();
			System.out.println("This is what you entered "+empName);
			
			System.out.println("Enter your Employee number");
			empId = in.nextInt();
			System.out.println("This is what you entered "+empId);
			
			String date = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").format(new Date());
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
			java.util.Date dateStr = formatter.parse(date);
			
			java.sql.Timestamp dateDB = new java.sql.Timestamp(dateStr.getTime());

			//to get a fingerprint from the user
			Runtime rt = Runtime.getRuntime();

			System.out.println("Waiting for you to scan your finger");
			Process fp = rt.exec("python2 /usr/share/doc/python-fingerprint/examples/example_search.py");

	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(fp.getInputStream()));
	        while ((s = stdInput.readLine()) != null) {
	        	System.out.println(s);
	            fingerPrint = s.substring(s.lastIndexOf(":") + 1).replaceAll("\\s", "");
	        }
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(fp.getErrorStream()));
	        // read any errors from the attempted command
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((s = stdError.readLine()) != null) {
	            System.out.println(s);
	            System.out.println(" here");
	        }
	        if(!fingerPrint.equals("Nomatchfound!"))
	        {
				java.sql.PreparedStatement insertthedataPS = null;
				System.out.println("here1");
				String insertthedataSQL = "insert into information_pi.info_fingerprint " +
										  "  (companyid, " +
						                  "  empname, " +
						                  "  fingerprint, " +
						                  "  date " +
						                  "  ) " +
										  "values (?,?,?,?)";
	
				insertthedataPS = con.prepareStatement(insertthedataSQL);
				insertthedataPS.setInt(1, empId);
				insertthedataPS.setString(2, empName);
				insertthedataPS.setString(3, fingerPrint);
				insertthedataPS.setTimestamp(4, dateDB);
	
				System.out.println(insertthedataPS);
				insertthedataPS.executeUpdate();
	        }
	        else
	        {
	        	System.out.println("Please try again");
	        }
	        System.exit(0);

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}
