import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class MFStructOrig
{
	public ArrayList<String> lst_Select_Attr = new ArrayList<String>();
	public int num_Grouping_Vari = 0;
	public ArrayList<String> lst_Grouping_Attr = new ArrayList<String>();
	public ArrayList<String> lst_FV = new ArrayList<String>();
	public ArrayList<String> lst_Conditions = new ArrayList<String>();
	
	public MFStructOrig(MFStructOrig orig)
	{
		this.lst_Select_Attr = orig.lst_Select_Attr;
		this.num_Grouping_Vari = orig.num_Grouping_Vari;
		this.lst_Grouping_Attr = orig.lst_Grouping_Attr;
		this.lst_FV = orig.lst_FV;
		this.lst_Conditions = orig.lst_Conditions;
	}
	public MFStructOrig()
	{}

	public void setSelectAttributes(BufferedReader br, String curLine)
	{
//		System.out.println("Select attribute(s):");
		//Check output
		try
		{
			curLine = br.readLine();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		int j = 0;
		for(int i = 0; i != curLine.length(); i++)
		{
			if(curLine.charAt(i) == ',')
			{
				if(j == 0)
				{
					String strAttr = curLine.substring(j, i);
//					System.out.println(strAttr);
					//Check output
					this.lst_Select_Attr.add(strAttr);
					j = i;
				}
				else
				{
					while(curLine.charAt(j) == ',' || curLine.charAt(j) == ' ')
					{
						j++;
					}
					String strAttr = curLine.substring(j, i);
//					System.out.println(strAttr);
					//Check output
					this.lst_Select_Attr.add(strAttr);
					j = i;
				}
			}
			else if(i == curLine.length() - 1)
			{
				while(curLine.charAt(j) == ',' || curLine.charAt(j) == ' ')
				{
					j++;
				}
				String strAttr = curLine.substring(j, i + 1);
//				System.out.println(strAttr);
				//Check output
				this.lst_Select_Attr.add(strAttr);
			}
		}
		lst_Select_Attr = this.toLowerCase(lst_Select_Attr);
	}
	
	public void setGroupingAttrNumber(String curLine)
	{
//		System.out.println("Grouping variable number:");
		//Check output
		this.num_Grouping_Vari = Integer.parseInt(curLine);
//		System.out.println(this.num_Grouping_Vari);
//		System.out.println();
		//Check output
	}
	
	public void setGroupingAttrs(BufferedReader br, String curLine)
	{
//		System.out.println("Grouping attribute(s):");
		try
		{
			curLine = br.readLine();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		int j = 0;
		for(int i = 0; i != curLine.length(); i++)
		{
			if(curLine.charAt(i) == ',')
			{
				if(j == 0)
				{
					String strAttr = curLine.substring(j, i);
//					System.out.println(strAttr);
					//Check output
					this.lst_Grouping_Attr.add(strAttr);
					j = i;
				}
				else
				{
					while(curLine.charAt(j) == ',' || curLine.charAt(j) == ' ')
					{
						j++;
					}
					String strAttr = curLine.substring(j, i);
//					System.out.println(strAttr);
					//Check output
					this.lst_Grouping_Attr.add(strAttr);
					j = i;
				}
			}
			else if(i == curLine.length() - 1)
			{
				while(curLine.charAt(j) == ',' || curLine.charAt(j) == ' ')
				{
					j++;
				}
				String strAttr = curLine.substring(j, i + 1);
//				System.out.println(strAttr);
				//Check output
				this.lst_Grouping_Attr.add(strAttr);
			}
		}
		lst_Grouping_Attr = this.toLowerCase(lst_Grouping_Attr);
	}
	
	public void setFV(BufferedReader br, String curLine)
	{
//		System.out.println("F-VECT:");
		//Check output
		try
		{
			curLine = br.readLine();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		int j = 0;
		for(int i = 0; i != curLine.length(); i++)
		{
			if(curLine.charAt(i) == ',')
			{
				if(j == 0)
				{
					String strAttr = curLine.substring(j, i);
//					System.out.println(strAttr);
					//Check output
					this.lst_FV.add(strAttr);
					j = i;
				}
				else
				{
					while(curLine.charAt(j) == ',' || curLine.charAt(j) == ' ')
					{
						j++;
					}
					String strAttr = curLine.substring(j, i);
//					System.out.println(strAttr);
					//Check output
					this.lst_FV.add(strAttr);
					j = i;
				}
			}
			else if(i == curLine.length() - 1)
			{
				while(curLine.charAt(j) == ',' || curLine.charAt(j) == ' ')
				{
					j++;
				}
				String strAttr = curLine.substring(j, i + 1);
//				System.out.println(strAttr);
				//Check output
				this.lst_FV.add(strAttr);
			}
		}
		lst_FV = this.toLowerCase(lst_FV);
	}
	
	public void setConditions(BufferedReader br, String curLine)
	{
//		System.out.println("Conditions:");
		//Check output
		try
		{
			curLine = br.readLine();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		try {
			do
			{
				int j = 0;
				for(int i = 0; i != curLine.length(); i++)
				{
					if(curLine.charAt(i) == ',')
					{
						if(j == 0)
						{
							String strAttr = curLine.substring(j, i);
//							System.out.println(strAttr);
							this.lst_Conditions.add(strAttr);
							j = i;
						}
						else
						{
							while(curLine.charAt(j) == ',' || curLine.charAt(j) == ' ')
							{
								j++;
							}
							String strAttr = curLine.substring(j, i);
//							System.out.println(strAttr);
							this.lst_Conditions.add(strAttr);
							j = i;
						}
					}
					else if(i == curLine.length() - 1)
					{
						while(curLine.charAt(j) == ',' || curLine.charAt(j) == ' ')
						{
							j++;
						}
						String strAttr = curLine.substring(j, i + 1);
//						System.out.println(strAttr);
						this.lst_Conditions.add(strAttr);
					}
				}
			}
			while((curLine = br.readLine()) != null);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		lst_Conditions = this.toLowerCase(lst_Conditions);
	}
	
	private ArrayList<String> toLowerCase(ArrayList<String> lst)
	{
		for (int i = 0; i != lst.size(); i++)
		{
			lst.set(i, lst.get(i).toLowerCase());
		}
		return lst;
	}
}
