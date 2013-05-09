//This code is automatically generated.
//Generated time: Thu 2013.05.09 at 06:58:42 AM EDT
//
//How to run this code:
//compile:	javac GeneratedCode.java
//run:		java -classpath <Path to jdbc driver file>/postgresql.jar:. GeneratedCode

import java.sql.*;
import java.util.ArrayList;

public class GeneratedCode
{
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/DBMS";
	static final String user = "postgres";
	static final String password = "tongqiang";
	static Connection conn;
	@SuppressWarnings("unused")
	static public void main(String arg[])
	{
		ArrayList<MFStruct> lstMFStruct = new ArrayList<MFStruct>();
		try
		{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, user, password);
			System.out.println("[Results of the query]");
			String queryStr = "SELECT * FROM sales";
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
				if(stateTmp.equalsIgnoreCase("ny"))
				{
					if(lstMFStruct.size() == 0)
					{
						MFStruct mfStructTmp = new MFStruct();
						mfStructTmp.initialization_1(custTmp,quantTmp);
						lstMFStruct.add(mfStructTmp);
						continue;
					}
					for(int i = 0; i != lstMFStruct.size(); i++)
					{
						if(lstMFStruct.get(i).equals(custTmp) == true)
						{
							lstMFStruct.get(i).set_count_1();
							lstMFStruct.get(i).set_sum_quant_1(quantTmp);
							break;
						}
						if(i == lstMFStruct.size() - 1)
						{
							MFStruct mfStructTmp = new MFStruct();
							mfStructTmp.initialization_1(custTmp,quantTmp);
							lstMFStruct.add(mfStructTmp);
							break;
						}
					}
				}
				if(stateTmp.equalsIgnoreCase("nj"))
				{
					if(lstMFStruct.size() == 0)
					{
						MFStruct mfStructTmp = new MFStruct();
						mfStructTmp.initialization_2(custTmp,quantTmp);
						lstMFStruct.add(mfStructTmp);
						continue;
					}
					for(int i = 0; i != lstMFStruct.size(); i++)
					{
						if(lstMFStruct.get(i).equals(custTmp) == true)
						{
							lstMFStruct.get(i).set_count_2();
							lstMFStruct.get(i).set_sum_quant_2(quantTmp);
							lstMFStruct.get(i).set_avg_quant_2(quantTmp);
							lstMFStruct.get(i).set_max_quant_2(quantTmp);
							break;
						}
						if(i == lstMFStruct.size() - 1)
						{
							MFStruct mfStructTmp = new MFStruct();
							mfStructTmp.initialization_2(custTmp,quantTmp);
							lstMFStruct.add(mfStructTmp);
							break;
						}
					}
				}
				if(stateTmp.equalsIgnoreCase("ct"))
				{
					if(lstMFStruct.size() == 0)
					{
						MFStruct mfStructTmp = new MFStruct();
						mfStructTmp.initialization_3(custTmp,quantTmp);
						lstMFStruct.add(mfStructTmp);
						continue;
					}
					for(int i = 0; i != lstMFStruct.size(); i++)
					{
						if(lstMFStruct.get(i).equals(custTmp) == true)
						{
							lstMFStruct.get(i).set_count_3();
							break;
						}
						if(i == lstMFStruct.size() - 1)
						{
							MFStruct mfStructTmp = new MFStruct();
							mfStructTmp.initialization_3(custTmp,quantTmp);
							lstMFStruct.add(mfStructTmp);
							break;
						}
					}
				}
			}
			System.out.println("cust" + "\t" + "1_sum_quant" + "\t" + "2_count_quant" + "\t" + "2_max_quant" + "\t" + "2_avg_quant" + "\t" + "2_sum_quant" + "\t" + "3_count_quant");
			for(int i = 0; i != lstMFStruct.size(); i++)
			{
				System.out.println(lstMFStruct.get(i).cust + "\t" 
					+ lstMFStruct.get(i).sum_quant_1 + "\t" 
					+ lstMFStruct.get(i).count_2 + "\t" 
					+ lstMFStruct.get(i).max_quant_2 + "\t" 
					+ lstMFStruct.get(i).avg_quant_2 + "\t" 
					+ lstMFStruct.get(i).sum_quant_2 + "\t" 
					+ lstMFStruct.get(i).count_3);
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
