//A pair Structure. A useful tool, to store all kinds of data.
public class Pair<T, U>
{
	//override datatypes
	public T t;
	public U u;

	//constructor
	public Pair()
	{
		t = null;
		u = null;
	}
	
	//override constructor
	public Pair(T t, U u)
	{         
		this.t= t;
		this.u= u;
	}

	//set data
	public void put(T t1, U u1)
	{
		this.t = t1;
		this.u = u1;
	}

	//get first data
	public T getFirst()
	{
		return t;
	}

	//get second data
	public U getSecond()
	{
		return u;
	}
	
	public void setSecond(U u1)
	{
		u = u1;
	}
	
	//check equal
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Pair))
		{
			return false;
		}
		if(obj == this)
		{
			return true;
		}
		if(obj.equals(null))
		{
			return false;
		}
		@SuppressWarnings("unchecked")
		Pair<T,	U> p = (Pair<T, U>)obj;
		if(p.getFirst() == null || p.getSecond() == null)
		{
			System.out.println("Pair NULL Error!");
			return false;
		}
		if(p.getFirst().equals(t) && p.getSecond().equals(u))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}