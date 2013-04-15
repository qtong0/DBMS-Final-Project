
public class MF_struct
{
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
		classStr = "public class ";
		classStr += "MFStruct\n{\n";
		String columnName = null;
		
		for(int i = 0; i != mforig.lst_Select_Attr.size(); i++)
		{
			classStr += "\t";
			String columnOrig = mforig.lst_Select_Attr.get(i);
			columnName = getColumnName(columnOrig);
			String type = info.getTypeFromColumn(columnName);
			classStr += type;
			classStr += " ";
			classStr += convertVariableName(columnOrig);
			classStr += ";\n";
		}
		for(int i = 0; i != mforig.num_Grouping_Vari; i++)
		{
			classStr = classStr + "\tint count_" + Integer.toString(i+1) + ";\n";
		}
		if(columnName.contains("sum") != false)
		{
			for(int i = 0; i != mforig.num_Grouping_Vari; i++)
			{
				classStr += "\tint ";
				classStr += "sum_";
				classStr += mySumSubString(mforig.lst_FV.get(i));
				classStr = classStr + "_" + Integer.toString(i+1);
				classStr += ";\n";
			}
		}
		classStr += "}\n";
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
}
