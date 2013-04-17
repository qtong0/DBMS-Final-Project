import java.util.ArrayList;

public class MF_struct
{
	private ArrayList<Pair<String, String>> lstAllTypeName = null;
	private ArrayList<Pair<String, String>> lstGroupingTypeName = null;
	private ArrayList<Pair<String, String>> lstVFVar = null;
	private String structStr;
	private String classStr;
	public MF_struct()
	{
		structStr = null;
		classStr = null;
	}
	public void setStructString(MFstruct_orig mforig, InfoSchema info)
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
	
	public void setClassString(MFstruct_orig mforig, InfoSchema info)
	{
		lstAllTypeName = new ArrayList<Pair<String, String>>();
		classStr = "public class ";
		classStr += "MFStruct\n{\n";
		String columnName = null;
		
		for(int i = 0; i != mforig.lst_Select_Attr.size(); i++)
		{
			classStr += "\t";
			String columnOrig = mforig.lst_Select_Attr.get(i);
			columnName = getColumnName(columnOrig);
//			System.out.println(("[acb]" + columnName));
			//debugging
			String type = info.getTypeFromColumn(columnName);
			classStr += type;
			classStr += " ";
			classStr += convertVariableName(columnOrig);
			classStr += ";\n";
			Pair<String, String> pairTypeName = new Pair<String, String>(type, convertVariableName(columnOrig));
			lstAllTypeName.add(pairTypeName);
		}
		for(int i = 0; i != mforig.num_Grouping_Vari; i++)
		{
			classStr = classStr + "\tint count_" + Integer.toString(i+1) + ";\n";
			Pair<String, String> pairTypeName = new Pair<String, String>("int", "count_" + Integer.toString(i+1));
			lstAllTypeName.add(pairTypeName);
		}
		if(columnName.contains("sum") != false)
		{
			for(int i = 0; i != mforig.num_Grouping_Vari; i++)
			{
				String checkCName = mySumSubString(mforig.lst_FV.get(i));
				String type = info.getTypeFromColumn(checkCName);
				classStr += "\t";
				classStr += type;
				classStr += " sum_";
				classStr += checkCName;
				classStr = classStr + "_" + Integer.toString(i+1);
				classStr += ";\n";
				Pair<String, String> pairTypeName = new Pair<String, String>(type, "sum_" + checkCName + "_" + Integer.toString(i+1));
				lstAllTypeName.add(pairTypeName);
			}
		}
		this.setInitVar(mforig, info);
		this.setGroupingTypeName(mforig, info);
		classStr = this.addJavaInitFunction(mforig, info, classStr);
		classStr = this.addJavaEqualsFuction(classStr);
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
	
	private String convertVariableName(String name)
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
	
	private String addJavaInitFunction(MFstruct_orig mforig, InfoSchema info, String classStr)
	{
		classStr += "\n\tpublic void initialization(";
		for(int i = 0; i != lstGroupingTypeName.size(); i++)
		{
			String type = lstGroupingTypeName.get(i).getFirst();
			String name = lstGroupingTypeName.get(i).getSecond();
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
			classStr = classStr + type + " " + name;
			if(i != lstVFVar.size() - 1)
			{
				classStr += ",";
			}
		}
		classStr += ")\n\t{\n";
		for(int i = 0; i != lstAllTypeName.size(); i++)
		{
			classStr += "\t\t";
			String varName = lstAllTypeName.get(i).getSecond();
			classStr += varName;
			classStr += " = ";
			String type = info.getTypeFromColumn(varName);
			if(lstGroupingTypeName.contains(new Pair<String, String>(type, varName)) == true)
			{
				classStr += varName;
				classStr += "Tmp";
			}
			type = info.getTypeFromColumn(myInitSubString(varName));
			if(lstVFVar.contains(new Pair<String, String>(type, this.myInitSubString(varName))) == true)
			{
				classStr += this.myInitSubString(varName);
			}
			if(varName.contains("count"))
			{
				classStr += "1";
			}
			classStr += ";\n";
		}
		
		classStr += "\t}\n";
		return classStr;
	}
	
	private void setInitVar(MFstruct_orig mforig, InfoSchema info)
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
	
	private void setGroupingTypeName(MFstruct_orig mforig, InfoSchema info)
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
			tmpStr += " == ";
			tmpStr += this.lstGroupingTypeName.get(i).getSecond();
			tmpStr += "Tmp";
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
		tmpStr += "\t\t}\n\t}";
		return tmpStr;
	}
}
