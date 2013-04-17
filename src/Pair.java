public class Pair<T, U>
{         
	public T t;
	public U u;

	public Pair()
	{
		t = null;
		u = null;
	}
	public Pair(T t, U u)
	{         
		this.t= t;
		this.u= u;
	}

	public void put(T t1, U u1)
	{
		this.t = t1;
		this.u = u1;
	}

	public T getFirst()
	{
		return t;
	}

	public U getSecond()
	{
		return u;
	}
	
	public void setSecond(U u1)
	{
		u = u1;
	}
	
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