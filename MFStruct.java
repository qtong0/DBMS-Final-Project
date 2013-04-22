public class MFStruct
{
	String cust;
	int sum_quant_1;
	int sum_quant_2;
	int sum_quant_3;
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
		sum_quant_2 = quant;
		count_2 = 1;
	}

	public void initialization_3(String custTmp,int quant)
	{
		cust = custTmp;
		sum_quant_3 = quant;
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

	public void set_sum_quant_3(int quantTmp)
	{
		sum_quant_3+=quantTmp;
	}

}
