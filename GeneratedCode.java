//This code is automatically generated.
//Generated time: Fri 2013.04.12 at 01:09:52 AM EDT
//
//How to run this code:
//compile:	javac GeneratedCode.java
//run:		java -classpath <Path to jdbc driver file>/postgresql.jar:. GeneratedCode

import java.sql.*;
//import java.util.ArrayList;

public class GeneratedCode
{
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/DBMS";
	static final String user = "postgres";
	static final String password = "tongqiang";
	static Connection conn;
	static public void main(String arg[])
	{
//		ArrayList<MFStructure> lstFMFStruct = new ArrayList<MFStructure>();
		try
		{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, user, password);
			System.out.println("[Results of the query]");
			String queryStr = "SELECT * FROM SALES";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryStr);
			while(rs.next())
			{
				String custTmp = rs.getString("cust");
				String prodTmp = rs.getString("prod");
				int dayTmp = rs.getInt("day");
				int monthTmp = rs.getInt("month");
				int yearTmp = rs.getInt("year");
				String stateTmp = rs.getString("state");
				int quantTmp = rs.getInt("quant");
				System.out.println(custTmp + "\t" + prodTmp + "\t" + dayTmp + "\t" + monthTmp + "\t" + yearTmp + "\t" + stateTmp + "\t" + quantTmp);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
