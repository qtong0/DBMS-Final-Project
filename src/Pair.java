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
	
}