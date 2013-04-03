
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
		
		for(int i = 0; i != mforig.lst_Select_Attr.size(); i++)
		{
			structStr += "\t";
			String columnOrig = mforig.lst_Select_Attr.get(i);
			String columnName = getColumnName(columnOrig);
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
		structStr += "};";
	}
	
	public void setClassString(InfoSchema info)
	{
		classStr = "public class\n{\n";
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
		name += "_";
		name += numberStr;
		return name;
	}
}
