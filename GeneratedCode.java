//This code is automatically generated.
//Generated time: Wed 2013.04.17 at 05:30:30 PM EDT
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
			String queryStr = "SELECT * FROM CALLS";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryStr);
			while(rs.next())
			{
				String fromacTmp = rs.getString("fromac");
				String fromtelTmp = rs.getString("fromtel");
				String toacTmp = rs.getString("toac");
				String totelTmp = rs.getString("totel");
				String dateTmp = rs.getString("date");
				int lengthTmp = rs.getInt("length");
//				System.out.println(fromacTmp + "\t" + fromtelTmp + "\t" + toacTmp + "\t" + totelTmp + "\t" + dateTmp + "\t" + lengthTmp);
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
