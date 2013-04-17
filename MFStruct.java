public class MFStruct
{
	String fromac;
	String fromtel;
	int avg_length_1;
	int avg_length_2;
	int count_1;
	int count_2;

	public void initialization(String fromacTmp,String fromtelTmp,int length)
	{
		fromac = fromacTmp;
		fromtel = fromtelTmp;
		avg_length_1 = length;
		avg_length_2 = length;
		count_1 = 1;
		count_2 = 1;
	}

	public boolean equals(String fromacTmp,String fromtelTmp)
	{
		if(fromac == fromacTmp && fromtel == fromtelTmp)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
