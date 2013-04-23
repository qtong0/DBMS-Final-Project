import java.util.ArrayList;

public class GenerateMFStructCode
{
	private ArrayList<Pair<String, String>> lstAllTypeName = null;
	private ArrayList<Pair<String, String>> lstGroupingTypeName = null;
	private ArrayList<Pair<String, String>> lstVFVar = null;
	private ArrayList<String> lstOrigFunctions = new ArrayList<String>();
	private ArrayList<Pair<Integer, String>> lstFunctionNumberNames = 
			new ArrayList<Pair<Integer, String>>();
	private String structStr;
	private String classStr;
	private String strInitFunction;

	public GenerateMFStructCode()
	{
		structStr = null;
		classStr = null;
		strInitFunction = null;
	}

	public GenerateMFStructCode(GenerateMFStructCode orig)
	{
		this.lstAllTypeName = orig.lstAllTypeName;
		this.lstGroupingTypeName = orig.lstGroupingTypeName;
		this.lstVFVar = orig.lstVFVar;
		this.lstOrigFunctions = orig.lstOrigFunctions;
		this.lstFunctionNumberNames = orig.lstFunctionNumberNames;
		this.structStr = orig.structStr;
		this.classStr = orig.classStr;
		this.strInitFunction = orig.strInitFunction;
	}
	
	public ArrayList<Pair<Integer, String>> getFuncitonList()
	{
		return this.lstFunctionNumberNames;
	}
	
	public ArrayList<Pair<String, String>> getGroupingTypeNameList()
	{
		return this.lstGroupingTypeName;
	}
	
	public String getInitFunctionString()
	{
		return this.strInitFunction;
	}
	
	public ArrayList<Pair<String, String>> getAllTypeNameList()
	{
		return this.lstAllTypeName;
	}
	
	public void setStructString(MFStructOrig mforig, InfoSchema info)
	{
		structStr = "struct mf_struct\n{\n";
		String columnName = null;
		for(int i = 0; i != mforig.lst_Select_Attr.size(); i++)
		{
			structStr += "\t";
			String columnOrig = mforig.lst_Select_Attr.get(i);
			columnName = getColumnName(columnOrig);
			String type = info.getTypeFromColumn(columnName);
			structStr += type;
			structStr += " ";
			structStr += convertVariableName(columnOrig);
			if(columnOrig.equals("cust"))
			{
				structStr += "[10]";
			}
			structStr += ";\n";
		}
		structStr += "};\n";
	}

	public void setClassString(MFStructOrig mforig, InfoSchema info)
	{
		//get list of functions
		for(int i = 0; i != mforig.lst_FV.size(); i++)
		{
			if(mforig.lst_FV.get(i).contains("avg") && lstOrigFunctions.contains("avg") == false)
			{
				lstOrigFunctions.add("avg");
			}
			if(mforig.lst_FV.get(i).contains("max") && lstOrigFunctions.contains("max") == false)
			{
				lstOrigFunctions.add("max");
			}
			if(mforig.lst_FV.get(i).contains("count") && lstOrigFunctions.contains("count") == false)
			{
				lstOrigFunctions.add("count");
			}
			if(mforig.lst_FV.get(i).contains("min") && lstOrigFunctions.contains("min") == false)
			{
				lstOrigFunctions.add("min");
			}
			if(mforig.lst_FV.get(i).contains("sum") && lstOrigFunctions.contains("sum") == false)
			{
				lstOrigFunctions.add("sum");
			}
		}

		lstAllTypeName = new ArrayList<Pair<String, String>>();
		classStr = "public class ";
		classStr += "MFStruct\n{\n";
		String columnOrig = null;

		for(int i = 0; i != mforig.lst_Select_Attr.size(); i++)
		{
			columnOrig = mforig.lst_Select_Attr.get(i);
			String columnName = getColumnName(columnOrig);
			if(columnOrig.contains("count") == true)
			{
				continue;
			}
			String type = info.getTypeFromColumn(columnName);
			Pair<String, String> pairTypeName = new Pair<String, String>(type, 
					convertVariableName(columnOrig));
			if(this.lstAllTypeName.contains(new Pair<String, String>(type,
					convertVariableName(columnOrig))) == true)
			{
				continue;
			}
			classStr += "\t";
			classStr += type;
			classStr += " ";
			classStr += convertVariableName(columnOrig);
			classStr += ";\n";
			lstAllTypeName.add(pairTypeName);
			//Add sum_variables if not exist
			if(columnOrig.contains("max")||
					columnOrig.contains("avg")||columnOrig.contains("min")||
					columnOrig.contains("count") && columnOrig.contains("sum") == false)
			{
				String subName = this.mySumSubString(columnOrig);
				Pair<String, String> pairTypeName0 = new Pair<String, String>(type, "sum_" + 
						subName + "_" + this.getFunctionNameFirstNumber(columnOrig));
				if(this.lstAllTypeName.contains(pairTypeName0) == true)
				{
					continue;
				}
				else
				{
					classStr += "\t" + type + " sum_" + subName;
					classStr += "_" + this.getFunctionNameFirstNumber(columnOrig) + ";\n";
					lstAllTypeName.add(pairTypeName0);
				}
			}
		}

		//Add count_variables...
		for(int i = 0; i != mforig.num_Grouping_Vari; i++)
		{
			classStr = classStr + "\tint count_" + Integer.toString(i+1) + ";\n";
			Pair<String, String> pairTypeName = new Pair<String, String>("int", "count_" + 
					Integer.toString(i+1));
			lstAllTypeName.add(pairTypeName);
		}

		this.setInitVar(mforig, info);
		this.setGroupingTypeName(mforig, info);
		classStr = this.addJavaInitFunction(mforig, info, classStr);
		classStr = this.addJavaEqualsFuction(classStr);
		//Add sum function(s)
		if(lstOrigFunctions.contains("sum"))
		{
			classStr = this.addJavaSumFunction(classStr);
		}
		//Add avg function(s)
		if(lstOrigFunctions.contains("avg"))
		{
			classStr = this.addJavaAvgFunction(classStr);
		}
		//Add max function(s) and min function(s)
		if(lstOrigFunctions.contains("max"))
		{
			classStr = this.addJavaMaxFunction(classStr);
		}
		if(lstOrigFunctions.contains("min"))
		{
			classStr = this.addJavaMinFunction(classStr);
		}
		
		//Add all set_count_* functions...
		for(int i = 0; i != mforig.num_Grouping_Vari; i++)
		{
			classStr += "\n\tpublic void ";
			String functionName = "set_count_" + new Integer(i+1);
			classStr += functionName + "()\n";
//			String shortName = this.myInitSubString(name);
			classStr += "\t{\n";
			classStr += "\t\tcount_" + new Integer(i+1) + "++;\n";
			classStr += "\t}\n";
		}
		//This is the end.
		classStr += "\n}\n";
	}

	public String getStructStr()
	{
		return structStr;
	}

	public String getClassStr()
	{
		return classStr;
	}

	private String getColumnName(String str)
	{
		int j = 0;
		for(int i = str.length() - 1; i != 0; i--)
		{
			if(str.charAt(i) == '_')
			{
				j = i;
				break;
			}
			if(i == 1 && str.charAt(0) != '_')
			{
				return str;
			}
		}
		str = str.substring(j + 1, str.length());
		return str;
	}

	//Convert from 1_avg_length to avg_length_1
	public String convertVariableName(String name)
	{
		String numberStr = null;
		if(Character.isDigit(name.charAt(0)) )
		{
			int j = 0;
			for(int i = 0; i != name.length(); i++)
			{
				if(name.charAt(i) == '_')
				{
					j = i;
					break;
				}
			}
			numberStr = name.substring(0, j);
			name = name.substring(j + 1, name.length());
		}
		if(numberStr != null)
		{
			name += "_";
			name += numberStr;
		}
		return name;
	}

	private String mySumSubString(String name)
	{
		String sumColumnNameStr = null;
		if(Character.isDigit(name.charAt(0)))
		{
			int j = 0;
			for(int i = name.length(); i != 0; i--)
			{
				if(name.charAt(i - 1) == '_')
				{
					j = i;
					break;
				}
			}
			sumColumnNameStr = name.substring(j, name.length());
		}
		return sumColumnNameStr;
	}

	private String addJavaInitFunction(MFStructOrig mforig, InfoSchema info, String classStr)
	{
		for(int j = 0; j != mforig.num_Grouping_Vari; j++)
		{
			classStr += "\n\tpublic void initialization_" + new Integer(j+1) + "(";
			this.strInitFunction = new String();
			for(int i = 0; i != lstGroupingTypeName.size(); i++)
			{
				String type = lstGroupingTypeName.get(i).getFirst();
				String name = lstGroupingTypeName.get(i).getSecond();
				this.strInitFunction += name + "Tmp" + ",";
				classStr += type;
				classStr += " ";
				classStr += name;
				classStr += "Tmp";
				classStr += ",";
			}
			for(int i = 0; i != lstVFVar.size(); i++)
			{
				String type = lstVFVar.get(i).getFirst();
				String name = lstVFVar.get(i).getSecond();
				this.strInitFunction += name + "Tmp";
				classStr = classStr + type + " " + name;
				if(i != lstVFVar.size() - 1)
				{
					this.strInitFunction += ",";
					classStr += ",";
				}
			}
			this.strInitFunction += ");\n";
			classStr += ")\n\t{\n";
			for(int i = 0; i != lstAllTypeName.size(); i++)
			{
				String varName = lstAllTypeName.get(i).getSecond();
				if(Character.isDigit(varName.charAt(varName.length() - 1)) == false
						|| this.getVariableNumber(varName) == j + 1)
				{
					classStr += "\t\t";
					classStr += varName;
					classStr += " = ";
					String type = info.getTypeFromColumn(varName);
					if(lstGroupingTypeName.contains(new Pair<String, String>(type, varName)) == true)
					{
						classStr += varName;
						classStr += "Tmp";
					}
					type = info.getTypeFromColumn(myInitSubString(varName));
					if(lstVFVar.contains(new Pair<String, String>(type,
							this.myInitSubString(varName))) == true)
					{
						classStr += this.myInitSubString(varName);
					}
					if(varName.contains("count"))
					{
						classStr += "1";
					}
					classStr += ";\n";	
				}
			}
			classStr += "\t}\n";
		}
		return classStr;
	}

	private void setInitVar(MFStructOrig mforig, InfoSchema info)
	{
		lstVFVar = new ArrayList<Pair<String, String>>();
		for(int i = 0; i != mforig.lst_FV.size(); i ++)
		{
			int i0 = 0;
			if(Character.isDigit(mforig.lst_FV.get(i).charAt(0)))
			{
				String tmp = mforig.lst_FV.get(i);
				for(int j = tmp.length(); j != 0; j--)
				{
					if(tmp.charAt(j - 1) == '_')
					{
						i0 = j;
						break;
					}
				}
				String name = tmp.substring(i0, tmp.length());
				String type = info.getTypeFromColumn(name);
				Pair<String, String> pairTypeName = new Pair<String, String>(type, name);
				if(lstVFVar.contains(pairTypeName) == false)
				{
					lstVFVar.add(pairTypeName);
				}
			}
		}
	}

	private void setGroupingTypeName(MFStructOrig mforig, InfoSchema info)
	{
		lstGroupingTypeName = new ArrayList<Pair<String, String>>();
		for(int i = 0; i != mforig.lst_Grouping_Attr.size(); i++)
		{
			String name = mforig.lst_Grouping_Attr.get(i);
			String type = info.getTypeFromColumn(name);
			Pair<String, String> pairNameType = new Pair<String, String>(type, name);
			if(lstGroupingTypeName.contains(pairNameType) == false)
			{
				lstGroupingTypeName.add(pairNameType);
			}
		}
	}

	//cut out the avg_ or sum_ only really variable names are left.
	//avg_length_1 => length
	private String myInitSubString(String varName)
	{
		String tmpName = null;
		int j = 0;
		for(int i = 0; i != varName.length(); i++)
		{
			if(varName.charAt(i) == '_' && j == 0)
			{
				j = i;
				continue;
			}
			if(varName.charAt(i) == '_' && j != 0)
			{
				tmpName = varName.substring(j + 1, i);
				break;
			}
		}
		return tmpName;
	}

	private String addJavaEqualsFuction(String classStr)
	{
		String tmpStr = classStr;
		tmpStr += "\n\tpublic boolean equals(";
		for(int i = 0; i != this.lstGroupingTypeName.size(); i++)
		{
			tmpStr += this.lstGroupingTypeName.get(i).getFirst();
			tmpStr += " ";
			tmpStr += this.lstGroupingTypeName.get(i).getSecond();
			tmpStr += "Tmp";
			if(i != this.lstGroupingTypeName.size() - 1)
			{
				tmpStr += ",";
			}
		}
		tmpStr += ")\n\t{\n";
		tmpStr += "\t\tif(";
		for(int i = 0; i != this.lstGroupingTypeName.size(); i++)
		{
			tmpStr += this.lstGroupingTypeName.get(i).getSecond();
			tmpStr += ".equals(";
			tmpStr += this.lstGroupingTypeName.get(i).getSecond();
			tmpStr += "Tmp)";
			if(i != this.lstGroupingTypeName.size() - 1)
			{
				tmpStr += " && ";
			}
		}
		tmpStr += ")\n\t\t{\n";
		tmpStr += "\t\t\treturn true;\n";
		tmpStr += "\t\t}\n";
		tmpStr += "\t\telse\n\t\t{\n";
		tmpStr += "\t\t\treturn false;\n";
		tmpStr += "\t\t}\n\t}\n";
		return tmpStr;
	}

	//Get variable number
	//avg_length_1 => 1
	private int getVariableNumber(String name)
	{
		int ireturn = 0;
		for(int i = name.length(); i != 0 ; i--)
		{
			if(name.charAt(i - 1) == '_')
			{
				String sub = name.substring(i, name.length());
				ireturn = Integer.parseInt(sub);
				break;
			}
			else
			{
				continue;
			}
		}
		if(ireturn == 0)
		{
			System.out.println("Variable Number Error!");
		}
		return ireturn;
	}

	//Add avgFunction
	private String addJavaAvgFunction(String classStr)
	{
		for(int i = 0; i != this.lstAllTypeName.size(); i++)
		{
			if(this.lstAllTypeName.get(i).getSecond().contains("avg"))
			{
				String name = this.lstAllTypeName.get(i).getSecond();
				String type = this.lstAllTypeName.get(i).getFirst();
				classStr += "\n\tpublic void ";
				String functionName = "set_" + name;
				classStr += functionName;
				this.lstFunctionNumberNames.add(new Pair<Integer, String>(
						new Integer(this.getVariableNumber(functionName)), functionName));
				classStr += "(";
				classStr += type;
				classStr += " ";
				classStr += this.myInitSubString(name);
				classStr += "Tmp)\n";
				String shortName = this.myInitSubString(name);
				classStr += "\t{\n";
//				classStr += "\t\tcount_" + this.getVariableNumber(name) + "++;\n";
//				classStr += "\t\tsum_" + shortName + "_" + this.getVariableNumber(name) + " += " + 
//						shortName + "Tmp;\n";
				classStr += "\t\t" + name + " = " + "sum_" + shortName + "_"
							+ this.getVariableNumber(name) + 
						" / count_" + this.getVariableNumber(name) + ";\n";
				classStr += "\t}\n";
			}
		}
		return classStr;
	}

	private String addJavaSumFunction(String classStr)
	{
		for(int i = 0; i != this.lstAllTypeName.size(); i++)
		{
			if(this.lstAllTypeName.get(i).getSecond().contains("sum"))
			{
				String name = this.lstAllTypeName.get(i).getSecond();
				String type = this.lstAllTypeName.get(i).getFirst();
				classStr += "\n\tpublic void ";
				String functionName = "set_" + name;
				this.lstFunctionNumberNames.add(new Pair<Integer, String>(
						new Integer(this.getVariableNumber(functionName)), functionName));
				classStr += functionName;
				classStr += "(" + type + " " + this.myInitSubString(name) + "Tmp)\n";
				String shortName = this.myInitSubString(name);
				classStr += "\t{\n";
//				classStr += "\t\tcount_" + this.getVariableNumber(name) + "++;\n";
				classStr += "\t\tsum_" + shortName + "_" + this.getVariableNumber(name) + "+="+
						shortName + "Tmp;\n";
				classStr += "\t}\n";
			}
		}
		return classStr;
	}

	private String addJavaMaxFunction(String classStr)
	{
		for(int i = 0; i != this.lstAllTypeName.size(); i++)
		{
			if(this.lstAllTypeName.get(i).getSecond().contains("max"))
			{
				String name = this.lstAllTypeName.get(i).getSecond();
				String type = this.lstAllTypeName.get(i).getFirst();
				classStr += "\n\tpublic void ";
				String functionName = "set_" + name;
				this.lstFunctionNumberNames.add(new Pair<Integer, String>(
						new Integer(this.getVariableNumber(functionName)), functionName));
				classStr += functionName;
				classStr += "(" + type + " " + this.myInitSubString(name) + "Tmp)\n";
				String shortName = this.myInitSubString(name);
				classStr += "\t{\n";
//				classStr += "\t\tcount_" + this.getVariableNumber(name) + "++;\n";
				classStr += "\t\tif(" + shortName + "Tmp > " + name + ")\n";
				classStr += "\t\t{\n";
				classStr += "\t\t\t" + name + " = " + shortName + "Tmp;\n";
				classStr += "\t\t}\n\t}\n";
			}
		}
		return classStr;
	}

	private String addJavaMinFunction(String classStr)
	{
		for(int i = 0; i != this.lstAllTypeName.size(); i++)
		{
			if(this.lstAllTypeName.get(i).getSecond().contains("min"))
			{
				String name = this.lstAllTypeName.get(i).getSecond();
				String type = this.lstAllTypeName.get(i).getFirst();
				classStr += "\n\tpublic void ";
				String functionName = "set_" + name;
				this.lstFunctionNumberNames.add(new Pair<Integer, String>(
						new Integer(this.getVariableNumber(functionName)), functionName));
				classStr += functionName;
				classStr += "(" + type + " " + this.myInitSubString(name) + "Tmp)\n";
				String shortName = this.myInitSubString(name);
				classStr += "\t{\n";
//				classStr += "\t\tcount_" + this.getVariableNumber(name) + "++;\n";
				classStr += "\t\tif(" + shortName + "Tmp < " + name + ")\n";
				classStr += "\t\t{\n";
				classStr += "\t\t\t" + name + " = " + shortName + "Tmp;\n";
				classStr += "\t\t}\n\t}\n";
			}
		}
		return classStr;
	}
	
	//Get the number of a method.
	//1_avg_length => 1
	private int getFunctionNameFirstNumber(String fname)
	{
		int ireturn = 0;
		for(int i = 0; i != fname.length(); i++)
		{
			if(fname.charAt(i) == '_')
			{
				String tmp = fname.substring(0, i);
				ireturn = Integer.parseInt(tmp);
				break;
			}
		}
		if(ireturn == 0)
		{
			System.out.println("Error in getFunctionNameFirstNumber method!");
		}
		return ireturn;
	}
}
