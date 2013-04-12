import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GeneratedCode
{
	Date dNow = new Date();
	SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
	ArrayList<Pair<String, String>> lstInfoSchemaPair = null;
	String strTotalCode = null;
	//Head comments.
	String strStartComm = "//This code is automatically generated.\n"
			+ "//Generated time: " + ft.format(dNow)
			+ "\n//\n"
			+ "//How to run this code:\n"
			+ "//compile:\tjavac GeneratedCode.java\n"
			+ "//run:\t\tjava -classpath <Path to jdbc driver file>/postgresql.jar:. GeneratedCode\n\n";
	
	//The import... part
	String strImpt_0 = "import java.sql.*;\n"
			+ "//import java.util.ArrayList;\n\n";
	
	//Main class name
	String strClassName = "GeneratedCode";
	
	//class declaration
	String strClassDec_1 = "public class " + strClassName + "\n" + "{\n"
	+ "\t" + "static final String JDBC_DRIVER = \"org.postgresql.Driver\";\n"
	+ "\t" + "static final String DB_URL = \"jdbc:postgresql://localhost:5432/DBMS\";\n"
	+ "\t" + "static final String user = \"postgres\";\n"
	+ "\t" + "static final String password = \"tongqiang\";\n"
	+ "\t" + "static Connection conn;\n";
	
	//main function beginning and connecting to database
	String strMain_2 = "\t" + "static public void main(String arg[])\n" + "\t{\n"
	//ArrayList not ready, TODO!!!!!
	+ "//\t\tArrayList<MFStructure> lstFMFStruct = new ArrayList<MFStructure>();\n"
			+ "\t\t" + "try\n" + "\t\t{\n"
	+ "\t\t\tClass.forName(JDBC_DRIVER);\n"
			+ "\t\t\tconn = DriverManager.getConnection(DB_URL, user, password);\n"
	+ "\t\t\tSystem.out.println(\"[Results of the query]\");\n"
	+ "\t\t\tString queryStr = \"SELECT * FROM SALES\";\n"
	+ "\t\t\tStatement st = conn.createStatement();\n"
	+ "\t\t\tResultSet rs = st.executeQuery(queryStr);\n";
	
	//first scan, while loop
	String strFirstScan_3 = "\t\t\twhile(rs.next())\n"
			+ "\t\t\t{\n";
	
	//TODO part, first just printout everything
	String strTmpTodo = null;
	
	String strCatchPart_end = "\t\t}\n"
			+ "\t\tcatch (SQLException e)\n"
			+ "\t\t{\n"
			+ "\t\t\te.printStackTrace();\n"
			+ "\t\t}\n"
			+ "\t\tcatch (ClassNotFoundException e)\n"
			+ "\t\t{\n"
			+ "\t\t\te.printStackTrace();\n"
			+ "\t\t}\n"
			+ "\t}\n}\n";
	
	public void setList(ArrayList<Pair<String, String>> lst)
	{
		lstInfoSchemaPair = new ArrayList<Pair<String, String>>(lst.size());
		for(int i = 0; i != lst.size(); i++)
		{
			lstInfoSchemaPair.add(lst.get(i));
		}
		strTmpTodo = this.generateTmpString();
	}
	
	public void printGCode()
	{
		if(strTmpTodo != null)
		{
			strTotalCode = strStartComm + strImpt_0 + strClassDec_1 + strMain_2 + strFirstScan_3 + strTmpTodo + strCatchPart_end;
			System.out.println(strTotalCode);
			//generate a java file
			try
			{
				PrintWriter pwout = new PrintWriter(new FileWriter("./GeneratedCode.java"));
				pwout.print(strTotalCode);
				pwout.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	//just a tmp example, to print out everything in the database.
	private String generateTmpString()
	{
		String tmp = new String();
		for(int i = 0; i != lstInfoSchemaPair.size(); i++)
		{
			String name = lstInfoSchemaPair.get(i).getFirst();
			String type = lstInfoSchemaPair.get(i).getSecond();
			String typeGetMethod = type.substring(0, 1).toUpperCase() + type.substring(1);
			tmp = tmp + "\t\t\t\t" + type + " " + name + "Tmp" + " = rs.get" + typeGetMethod + "(\""
					+ name + "\");\n";
		}
		tmp += "\t\t\t\tSystem.out.println(";
		for(int i = 0; i != lstInfoSchemaPair.size(); i++)
		{
			if(i == lstInfoSchemaPair.size() - 1)
			{
				tmp = tmp + lstInfoSchemaPair.get(i).getFirst() + "Tmp" + ");\n";
			}
			else
			{
				tmp = tmp + lstInfoSchemaPair.get(i).getFirst() + "Tmp" + " + \"\\t\" + ";
			}
		}
		tmp += "\t\t\t}\n";
		return tmp;
	}
}
