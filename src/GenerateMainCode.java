import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GenerateMainCode
{
	GenerateMFStructCode genMFStructcode = null;
	MFStructOrig mfstructOrig = null;
	Date dNow = new Date();
	SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
	ArrayList<Pair<String, String>> lstInfoSchemaPair = new ArrayList<Pair<String, String>>();
	SelectConVectConverter mySCVConverter;
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
			+ "import java.util.ArrayList;\n\n";
	
	//Main class name
	String strClassName = "GeneratedCode";
	
	//class declaration
	String strClassDec_1 = "public class " + strClassName + "\n" + "{\n"
	+ "\t" + "static final String JDBC_DRIVER = \"org.postgresql.Driver\";\n"
	+ "\t" + "static final String DB_URL = \"jdbc:postgresql://localhost:5432/DBMS\";\n"
	+ "\t" + "static final String user = \"postgres\";\n"
	+ "\t" + "static final String password = \"tongqiang\";\n"
	+ "\t" + "static Connection conn;\n"
	+ "\t" + "@SuppressWarnings(\"unused\")\n";
	
	//main function beginning and connecting to database
	String strMain_2 = "\t" + "static public void main(String arg[])\n" + "\t{\n"
	//ArrayList not ready, TODO!!!!!
	+ "\t\tArrayList<MFStruct> lstMFStruct = new ArrayList<MFStruct>();\n"
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
	String strSinpleScan = null;
	
	String strPrintResults = null;
	
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
	
	//Constructor...
	public GenerateMainCode(MFStructOrig mfStructOrig, GenerateMFStructCode mfstruct, InfoSchema info)
	{
		lstInfoSchemaPair = info.getList();
		this.genMFStructcode = new GenerateMFStructCode(mfstruct);
		mySCVConverter = new SelectConVectConverter(mfStructOrig);
		this.mfstructOrig = new MFStructOrig(mfStructOrig);
		strSinpleScan = this.generateFirstScan();
		strPrintResults = this.printResults();
	}
	
	public void printGCode()
	{
		if(strSinpleScan != null)
		{
			strTotalCode = strStartComm + strImpt_0 + strClassDec_1 + strMain_2 + strFirstScan_3 + strSinpleScan + strPrintResults + strCatchPart_end;
			//generate a java file
			try
			{
				PrintWriter pwoutMain = new PrintWriter(new FileWriter("./GeneratedCode.java"));
				pwoutMain.print(strTotalCode);
				System.out.println("File GeneratedCode.java successfully generated!");
				pwoutMain.close();
				PrintWriter pwoutMFstruct = new PrintWriter(new FileWriter("./MFStruct.java"));
				pwoutMFstruct.print(this.genMFStructcode.getClassStr());
				System.out.println("File MFStruct.java successfully generated!");
				pwoutMFstruct.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	//The first while() loop, for the first scan.
	private String generateFirstScan()
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
		//Follow code is to print all data. SELECT * FROM <TABLE>
		//tmp = this.myPrintAll(lstInfoSchemaPair, tmp);
		
		for(int i = 0; i != this.mfstructOrig.num_Grouping_Vari; i++)
		{
			tmp += "\t\t\t\tif(" + this.mySCVConverter.lstJavaCodeConditions.get(i).getSecond() + ")\n";
			tmp += "\t\t\t\t{\n";
			tmp += "\t\t\t\t\tif(lstMFStruct.size() == 0)\n";
			tmp += "\t\t\t\t\t{\n";
			tmp += "\t\t\t\t\t\tMFStruct mfStructTmp = new MFStruct();\n";
			tmp += "\t\t\t\t\t\tmfStructTmp.initialization_" + new Integer(i+1) + "(";
			tmp += this.genMFStructcode.getInitFunctionString();
			tmp += "\t\t\t\t\t\tlstMFStruct.add(mfStructTmp);\n";
			tmp += "\t\t\t\t\t\tcontinue;\n";
			tmp += "\t\t\t\t\t}\n";
			tmp += "\t\t\t\t\tfor(int i = 0; i != lstMFStruct.size(); i++)\n";
			tmp += "\t\t\t\t\t{\n";
			tmp += "\t\t\t\t\t\tif(lstMFStruct.get(i).equals(";
			for(int i0 = 0; i0 != this.genMFStructcode.getGroupingTypeNameList().size(); i0++)
			{
				tmp += this.genMFStructcode.getGroupingTypeNameList().get(i0).getSecond() + "Tmp";
				if(i0 != this.genMFStructcode.getGroupingTypeNameList().size() - 1)
				{
					tmp += ",";
				}
			}
			tmp += ") == true)\n";
			tmp += "\t\t\t\t\t\t{\n";
			
			ArrayList<Pair<Integer, String>> tmpList = new ArrayList<Pair<Integer, String>>(
					this.genMFStructcode.getFuncitonList());
			for(int j = 0; j != tmpList.size(); j++)
			{
				if(tmpList.get(j).getFirst().equals(i+1))
				{
					tmp += "\t\t\t\t\t\t\tlstMFStruct.get(i).";
					tmp += tmpList.get(j).getSecond() + "(" + 
					this.myInitSubString(tmpList.get(j).getSecond()) + "Tmp" + ");\n";
				}
			}
			tmp += "\t\t\t\t\t\t\tbreak;\n";
			tmp += "\t\t\t\t\t\t}\n";
			
			tmp += "\t\t\t\t\t\tif(i == lstMFStruct.size() - 1)\n";
			tmp += "\t\t\t\t\t\t{\n";
			tmp += "\t\t\t\t\t\t\tMFStruct mfStructTmp = new MFStruct();\n";
			tmp += "\t\t\t\t\t\t\tmfStructTmp.initialization_" + new Integer(i+1) + "(" + this.genMFStructcode.getInitFunctionString();
			tmp += "\t\t\t\t\t\t\tlstMFStruct.add(mfStructTmp);\n";
			tmp += "\t\t\t\t\t\t\tbreak;\n";
			tmp += "\t\t\t\t\t\t}\n";
			tmp += "\t\t\t\t\t}\n";
			tmp += "\t\t\t\t}\n";
		}
		tmp += "\t\t\t}\n";
		return tmp;
	}
	
	private String printResults()
	{
		String strReturn = "\t\t\t//to print out the results.\n";
		strReturn = "\t\t\tSystem.out.println(";
		for(int i = 0; i != this.mfstructOrig.lst_Select_Attr.size(); i++)
		{
			strReturn += "\"" + this.mfstructOrig.lst_Select_Attr.get(i) + "\"";
			if(i != this.mfstructOrig.lst_Select_Attr.size() - 1)
			{
				strReturn += " + \"\\t\" + ";
			}
		}
		strReturn += ");\n";
		strReturn += "\t\t\tfor(int i = 0; i != lstMFStruct.size(); i++)\n";
		strReturn += "\t\t\t{\n";
		strReturn += "\t\t\t\tSystem.out.println(";
		ArrayList<String> tmpList = this.mfstructOrig.lst_Select_Attr;
		for(int i = 0; i != tmpList.size(); i++)
		{
			strReturn += "lstMFStruct.get(i)."
					+ new GenerateMFStructCode().convertVariableName(tmpList.get(i));
			if(i != tmpList.size() - 1)
			{
				strReturn += " + \"\\t\" \n\t\t\t\t\t+ ";
			}
		}
		strReturn += ");\n";
		strReturn += "\t\t\t}\n";
		return strReturn;
	}
	
	//try to get the sub string from method name
	//set_max_length_1 => length
	private String myInitSubString(String name)
	{
		String strTmp = null;
		int j = 0;
		for(int i = name.length() - 1; i !=  0; i--)
		{
			if(name.charAt(i) == '_' && j == 0)
			{
				j = i;
				continue;
			}
			if(name.charAt(i) == '_' && j != 0)
			{
				strTmp = name.substring(i + 1, j);
				break;
			}
		}
		if(strTmp == null)
		{
			System.out.println("Substring Error from GMainCode!");
		}
		return strTmp;
	}
	
	//To printout all stuffs, SELECT * FROM <TABLE>
	@SuppressWarnings("unused")
	private String myPrintAll(ArrayList<Pair<String, String>> lstInfoSchemaPair, String tmp)
	{
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
		return tmp;
	}
}
