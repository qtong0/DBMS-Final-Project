import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBMS
{
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/DBMS";
	static final String user = "postgres";
	static final String password = "tongqiang";
	static final MFstruct_orig MFStructOrig = new MFstruct_orig();
	static final InfoSchema infoSchema = new InfoSchema();
	static final MF_struct mfstruct = new MF_struct();
	

	static Connection conn;
	
	public static void main(String arg[])
	{
		String filePath = "./example.txt";
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
			infoSchema.setStructTypeJAVA();
//			printInfoSchema(infoSchema);
			//Check output
			
			mfstruct.setClassString(MFStructOrig, infoSchema);
			
			GenerateCode gCode = new GenerateCode(mfstruct);
			gCode.setInfoSchemaList(infoSchema.getList());
			gCode.printGCode();
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
				if (br != null)
				{
					br.close();
				}
			}
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}
	
	private static void checkInfoSchema()
	{
		String SQLQuery = "select column_name, data_type from information_schema.columns\n"
				+ "where table_name = 'calls'";
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
//			printInfoSchema(infoSchema);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private static void printInfoSchema(InfoSchema infoSchema)
	{
		for(int i = 0; i != infoSchema.getList().size(); i++)
		{
			System.out.println( infoSchema.getList().get(i).getFirst() + "\t"
					+ infoSchema.getList().get(i).getSecond());
		}
	}
}
