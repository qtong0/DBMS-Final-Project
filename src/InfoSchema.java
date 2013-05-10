//Check Informations_schema from database

import java.util.ArrayList;

public class InfoSchema
{
	//to store the type-name pair of every column in the table
	private ArrayList<Pair<String, String>> lstPair = new ArrayList<Pair<String, String>>();

	public void addValue(String column, String type)
	{
		if(column != null && type != null)
		{
			Pair<String, String> pairColType = new Pair<String, String>();
			pairColType.put(column, type);
			this.lstPair.add(pairColType);
		}
	}
	
	//CType code, not ready yet.
	public void setStructTypeC()
	{
		for(int i = 0; i != lstPair.size(); i++)
		{
			String typeOrigName = lstPair.get(i).getSecond();
			if(typeOrigName.equals("character varying"))
			{
				lstPair.get(i).setSecond("char");
			}
			else if(typeOrigName.equals("integer"))
			{
				lstPair.get(i).setSecond("int");
			}
			else if(typeOrigName.equals("character") && lstPair.get(i).getFirst().equals("state"))
			{
				lstPair.get(i).setSecond("char");
			}
			else if(typeOrigName.equals("character")
					&& (lstPair.get(i).getFirst().equals("state") != true))
			{
				lstPair.get(i).setSecond("char");
			}
		}
	}
	
	//JAVA type code, ready to go.
	public void setStructTypeJAVA()
	{
		for(int i = 0; i != lstPair.size(); i++)
		{
			String typeOrigName = lstPair.get(i).getSecond();
			if(typeOrigName.equals("character varying"))
			{
				lstPair.get(i).setSecond("String");
			}
			else if(typeOrigName.equals("integer"))
			{
				lstPair.get(i).setSecond("int");
			}
			else if(typeOrigName.equals("character"))
			{
				lstPair.get(i).setSecond("String");
			}
		}
	}
	
	//get the type from the column name
	public String getTypeFromColumn(String column)
	{
		for(int i = 0; i != lstPair.size(); i++)
		{
			if(lstPair.get(i).getFirst().equals(column))
			{
				return lstPair.get(i).getSecond();
			}
		}
		return null;
	}
	
	//return pair list.
	public ArrayList<Pair<String, String>> getList()
	{
		return lstPair;
	}
}
