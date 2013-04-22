//This class deal with Select Condition-Vect...

import java.util.ArrayList;

public class SelectConVectConverter
{
	MFStructOrig mfstruct;
	ArrayList<String> lstConVects = new ArrayList<String>();
	ArrayList<Pair<Integer, String>> lstJavaCodeConditions = new ArrayList<Pair<Integer, String>>();
	//Constructor!
	public SelectConVectConverter(MFStructOrig orig)
	{
		mfstruct = orig;
		this.lstConVects = orig.lst_Conditions;
		this.setCodeList();
	}
	
	private void setCodeList()
	{
		for(int i = 0; i != this.lstConVects.size(); i++)
		{
			String tmpStr = this.lstConVects.get(i);
			this.setPairIntString(tmpStr);
		}
	}
	
	private void setPairIntString(String tmpStr)
	{
		for(int i = 0; i != tmpStr.length(); i++)
		{
			if(tmpStr.charAt(i) == '.')
			{
				String parsedStr = tmpStr.substring(i + 1, tmpStr.length());
				String subName = null, type = null;
				parsedStr = parsedStr.replaceAll("\\s", "");
				parsedStr = parsedStr.replace('\'', '\"');
				for(int i1 = 0; i1 != DBMS.infoSchema.getList().size(); i1++)
				{
					subName = DBMS.infoSchema.getList().get(i1).getFirst();
					if(parsedStr.contains(subName))
					{
						type = DBMS.infoSchema.getTypeFromColumn(subName);
						parsedStr = parsedStr.replace(subName, subName + "Tmp");
					}
				}
				int itmp = 0;
				for(int j = 0; j != parsedStr.length(); j++)
				{
					if(parsedStr.charAt(j) == '=')
					{
						itmp++;
					}
					if(itmp > 2)
					{
						System.out.println("Input Error!");
					}
				}
				if(parsedStr.contains("=="))
				{
					if(type.equals("String"))
					{
						parsedStr = parsedStr.replace("=", ".equalsIgnoreCase(");
						parsedStr += ")";
					}
					else
					{
						parsedStr = parsedStr.replace("==", ".equals(");
						parsedStr += ")";
					}
				}
				if(parsedStr.contains("="))
				{
					if(type.equals("String"))
					{
						parsedStr = parsedStr.replace("=", ".equalsIgnoreCase(");
						parsedStr += ")";
					}
					else
					{
						parsedStr = parsedStr.replace("=", ".equals(");
						parsedStr += ")";
					}
				}
				String tmp = tmpStr.substring(0, i);
				int j = Integer.parseInt(tmp);
				this.lstJavaCodeConditions.add(new Pair<Integer, String>(j, parsedStr));
			}
		}
	}
	
}
