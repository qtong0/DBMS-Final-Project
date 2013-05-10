//small class to store all DB data
//includes username, password, DBURL, DBtablename

public class UI_Information
{
	String strDBUserName = new String();
	String strDBPSW = new String();
	String strDBURL = new String();
	String strTableName = new String();
	
	public UI_Information()
	{
		strDBURL = "jdbc:postgresql://localhost:5432/DBMS";
		strDBUserName = "postgres";
		strDBPSW = "tongqiang";
		strTableName = "sales";
	}
	
	public UI_Information(UI_Information orig)
	{
		this.strDBPSW = orig.strDBPSW;
		this.strDBURL = orig.strDBURL;
		this.strDBUserName = orig.strDBUserName;
		this.strTableName = orig.strTableName;
	}
}
