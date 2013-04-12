import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class MFstruct_orig
{
	public ArrayList<String> lst_Select_Attr = new ArrayList<String>();
	public int num_Grouping_Vari = 0;
	public ArrayList<String> lst_Grouping_Attr = new ArrayList<String>();
	public ArrayList<String> lst_FV = new ArrayList<String>();
	public ArrayList<String> lst_conditions = new ArrayList<String>();
	
	public void setSelectAttributes(BufferedReader br, String curLine)
	{
		System.out.println("Select attribute(s):");
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
					System.out.println(strAttr);
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
					System.out.println(strAttr);
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
				System.out.println(strAttr);
				this.lst_Select_Attr.add(strAttr);
			}
		}
		System.out.println();
	}
	
	public void setGroupingAttrNumber(String curLine)
	{
		System.out.println("Grouping variable number:");
		this.num_Grouping_Vari = Integer.parseInt(curLine);
		System.out.println(this.num_Grouping_Vari);
		System.out.println();
	}
	
	public void setGroupingAttrs(BufferedReader br, String curLine)
	{
		System.out.println("Grouping attribute(s):");
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
					System.out.println(strAttr);
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
					System.out.println(strAttr);
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
				System.out.println(strAttr);
				this.lst_Grouping_Attr.add(strAttr);
			}
		}
		System.out.println();
	}
	
	public void setFV(BufferedReader br, String curLine)
	{
		System.out.println("F-VECT:");
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
					System.out.println(strAttr);
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
					System.out.println(strAttr);
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
				System.out.println(strAttr);
				this.lst_FV.add(strAttr);
			}
		}
		System.out.println();
	}
	
	public void setConditions(BufferedReader br, String curLine)
	{
		System.out.println("Conditions:");
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
							System.out.println(strAttr);
							this.lst_conditions.add(strAttr);
							j = i;
						}
						else
						{
							while(curLine.charAt(j) == ',' || curLine.charAt(j) == ' ')
							{
								j++;
							}
							String strAttr = curLine.substring(j, i);
							System.out.println(strAttr);
							this.lst_conditions.add(strAttr);
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
						System.out.println(strAttr);
						this.lst_conditions.add(strAttr);
					}
				}
			}
			while((curLine = br.readLine()) != null);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println();
	}
}
