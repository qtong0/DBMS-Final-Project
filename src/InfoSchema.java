
import java.util.ArrayList;

public class InfoSchema
{

	ArrayList<Pair<String, String>> lstPair = new ArrayList<Pair<String, String>>();

	public void addValue(String column, String type)
	{
		if(column != null && type != null)
		{
			Pair<String, String> pairColType = new Pair<String, String>();
			pairColType.put(column, type);
			this.lstPair.add(pairColType);
		}
	}
	
	public void setStructType()
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
}
