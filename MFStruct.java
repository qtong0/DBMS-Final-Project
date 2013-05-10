//This code is automatically generated.
//Generated Time:Fri 2013.05.10 at 01:20:17 AM EDT.
//
//This code is the Class help implement the methods like avg, sum, max...

public class MFStruct
{
	//all variables...
	String fromac;
	String fromtel;
	int sum_length_1;
	int avg_length_2;
	int sum_length_2;
	int count_1;
	int count_2;

	//initialize the variables in each group.
	public void initialization_1(String fromacTmp,String fromtelTmp,int length)
	{
		fromac = fromacTmp;
		fromtel = fromtelTmp;
		sum_length_1 = length;
		count_1 = 1;
	}

	public void initialization_2(String fromacTmp,String fromtelTmp,int length)
	{
		fromac = fromacTmp;
		fromtel = fromtelTmp;
		avg_length_2 = length;
		sum_length_2 = length;
		count_2 = 1;
	}

	//equals() fucntion.
	public boolean equals(String fromacTmp,String fromtelTmp)
	{
		if(fromac.equals(fromacTmp) && fromtel.equals(fromtelTmp))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//sum functions.
	public void set_sum_length_1(int lengthTmp)
	{
		sum_length_1+=lengthTmp;
	}
	public void set_sum_length_2(int lengthTmp)
	{
		sum_length_2+=lengthTmp;
	}

	//average functions.
	public void set_avg_length_2(int lengthTmp)
	{
		avg_length_2 = sum_length_2 / count_2;
	}

	public void set_count_1()
	{
		count_1++;
	}

	public void set_count_2()
	{
		count_2++;
	}

}
