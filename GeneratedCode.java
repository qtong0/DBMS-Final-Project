//This code is automatically generated.
//Generated time: Fri 2013.05.10 at 01:20:17 AM EDT
//
//How to run this code:
//compile:	javac GeneratedCode.java
//run:		java -classpath <Path to jdbc driver file>/postgresql.jar:. GeneratedCode

import java.sql.*;
import java.util.ArrayList;

public class GeneratedCode
{
	//Declare and initialize all types and data.
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/DBMS";
	static final String user = "postgres";
	static final String password = "tongqiang";
	static Connection conn;
	//There might be some unused datatypes, eliminate warning.
	@SuppressWarnings("unused")

	//main method...
	static public void main(String arg[])
	{
		//ArrayList stores all the MFStruct type of data.
		ArrayList<MFStruct> lstMFStruct = new ArrayList<MFStruct>();
		try
		{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, user, password);
			System.out.println("[Results of the query]");
			String queryStr = "SELECT * FROM calls";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryStr);
			//Scan the whole database.
			while(rs.next())
			{
				String fromacTmp = rs.getString("fromac");
				String fromtelTmp = rs.getString("fromtel");
				String toacTmp = rs.getString("toac");
				String totelTmp = rs.getString("totel");
				String dateTmp = rs.getString("date");
				int lengthTmp = rs.getInt("length");
				//#1 Selection Conditions.
				if(toacTmp.equalsIgnoreCase("201"))
				{
					if(lstMFStruct.size() == 0)
					{
						MFStruct mfStructTmp = new MFStruct();
						mfStructTmp.initialization_1(fromacTmp,fromtelTmp,lengthTmp);
						lstMFStruct.add(mfStructTmp);
					}
					else
					{
						for(int i = 0; i != lstMFStruct.size(); i++)
						{
							if(lstMFStruct.get(i).equals(fromacTmp,fromtelTmp) == true)
							{
								lstMFStruct.get(i).set_count_1();
								lstMFStruct.get(i).set_sum_length_1(lengthTmp);
								break;
							}
							if(i == lstMFStruct.size() - 1)
							{
								MFStruct mfStructTmp = new MFStruct();
								mfStructTmp.initialization_1(fromacTmp,fromtelTmp,lengthTmp);
								lstMFStruct.add(mfStructTmp);
								break;
							}
						}
					}
				}
				//#2 Selection Conditions.
				if(toacTmp.equalsIgnoreCase("301"))
				{
					if(lstMFStruct.size() == 0)
					{
						MFStruct mfStructTmp = new MFStruct();
						mfStructTmp.initialization_2(fromacTmp,fromtelTmp,lengthTmp);
						lstMFStruct.add(mfStructTmp);
					}
					else
					{
						for(int i = 0; i != lstMFStruct.size(); i++)
						{
							if(lstMFStruct.get(i).equals(fromacTmp,fromtelTmp) == true)
							{
								lstMFStruct.get(i).set_count_2();
								lstMFStruct.get(i).set_sum_length_2(lengthTmp);
								lstMFStruct.get(i).set_avg_length_2(lengthTmp);
								break;
							}
							if(i == lstMFStruct.size() - 1)
							{
								MFStruct mfStructTmp = new MFStruct();
								mfStructTmp.initialization_2(fromacTmp,fromtelTmp,lengthTmp);
								lstMFStruct.add(mfStructTmp);
								break;
							}
						}
					}
				}
			}
			//to print out the results.
			System.out.printf("%-14s%-14s%14s%14s\n", "FROMAC" , "FROMTEL" , "1_SUM_LENGTH" , "2_AVG_LENGTH");
			for(int i = 0; i != lstMFStruct.size(); i++)
			{
				System.out.printf("%-14s%-14s%14s%14s\n", 
					 lstMFStruct.get(i).fromac , 
					 lstMFStruct.get(i).fromtel , 
					 lstMFStruct.get(i).sum_length_1 , 
					 lstMFStruct.get(i).avg_length_2);
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
