public class MFStruct
{
	String cust;
	int sum_quant_1;
	int max_quant_2;
	int sum_quant_2;
	int avg_quant_2;
	int count_1;
	int count_2;
	int count_3;

	public void initialization_1(String custTmp,int quant)
	{
		cust = custTmp;
		sum_quant_1 = quant;
		count_1 = 1;
	}

	public void initialization_2(String custTmp,int quant)
	{
		cust = custTmp;
		max_quant_2 = quant;
		sum_quant_2 = quant;
		avg_quant_2 = quant;
		count_2 = 1;
	}

	public void initialization_3(String custTmp,int quant)
	{
		cust = custTmp;
		count_3 = 1;
	}

	public boolean equals(String custTmp)
	{
		if(cust.equals(custTmp))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void set_sum_quant_1(int quantTmp)
	{
		sum_quant_1+=quantTmp;
	}

	public void set_sum_quant_2(int quantTmp)
	{
		sum_quant_2+=quantTmp;
	}

	public void set_avg_quant_2(int quantTmp)
	{
		avg_quant_2 = sum_quant_2 / count_2;
	}

	public void set_max_quant_2(int quantTmp)
	{
		if(quantTmp > max_quant_2)
		{
			max_quant_2 = quantTmp;
		}
	}

	public void set_count_1()
	{
		count_1++;
	}

	public void set_count_2()
	{
		count_2++;
	}

	public void set_count_3()
	{
		count_3++;
	}

}
