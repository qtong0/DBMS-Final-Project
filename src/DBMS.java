import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
//import java.util.Scanner;

public class DBMS
{
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/DBMS";
	static final String user = "postgres";
	static final String password = "tongqiang";
	static final MFstruct_orig MFStructOrig = new MFstruct_orig();
	static final InfoSchema infoSchema = new InfoSchema();
	
    
    
	static Connection conn;
	
	public static void main(String arg[])
	{
		String filePath = "/users/michaelt/desktop/test.txt";
		BufferedReader br = null;
		String curLine;
		try
		{
			br = new BufferedReader(new FileReader(filePath));
			while((curLine = br.readLine()) != null)
			{
				if(curLine.equals("SELECT ATTRIBUTE(S):"))
				{
					MFStructOrig.setSelectAttributes(br, curLine);
					continue;
				}
				else if(curLine.equals("NUMBER OF GROUPING VARIABLES(n):"))
				{
					curLine = br.readLine();
					MFStructOrig.setGroupingAttrNumber(curLine);
					continue;
				}
				else if(curLine.equals("GROUPING ATTRIBUTES(V);"))
				{
					MFStructOrig.setGroupingAttrs(br, curLine);
					continue;
				}
				else if(curLine.equals("F-VECT([F]):"))
				{
					MFStructOrig.setFV(br, curLine);
					continue;
				}
				else if(curLine.equals("SELECT CONDITION-VECT([]):"))
				{
					MFStructOrig.setConditions(br, curLine);
					continue;
				}
			}
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, user, password);
			checkInfoSchema();
			conn.close();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				if (br != null)br.close();
			}
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private static void doSelectTest()
	{
		System.out.println("[Results of Select Query]");
		String query = "select cust, prod from sales";
		try
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next())
			{
				String cust = rs.getString("cust");
				String prod = rs.getString("prod");
				System.out.println(cust + "\t" + prod);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private static void checkInfoSchema()
	{
		String SQLQuery = "select column_name, data_type from information_schema.columns\n"
				+ "where table_name = 'sales'";
		try
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQLQuery);
			while(rs.next())
			{
				String col = rs.getString("column_name");
				String type = rs.getString("data_type");
				infoSchema.addValue(col, type);
			}
			for(int i = 0; i != infoSchema.lstMap.size(); i++)
			{
				System.out.println( infoSchema.lstMap.get(i).getFirst() + "\t"
						+ infoSchema.lstMap.get(i).getSecond());
			}
			System.out.println();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}
