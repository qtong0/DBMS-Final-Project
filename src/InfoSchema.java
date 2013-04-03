
import java.util.ArrayList;

public class InfoSchema
{

	ArrayList<Pair<String, String>> lstMap = new ArrayList<Pair<String, String>>();

	public void addValue(String column, String type)
	{
		if(column != null && type != null)
		{
			Pair<String, String> pairColType = new Pair<String, String>();
			pairColType.put(column, type);
			this.lstMap.add(pairColType);
		}
	}
}
