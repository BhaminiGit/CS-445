package cs445.a1;

public class Set<E> implements SetInterface<E> {

	private int size;
	private E[] setar;  

	public Set() {
		
		setar = (E[]) new Object[30];
		size = 0;
		
	}

	public Set(int capacity) {
		
		setar = (E[]) new Object[capacity];
		size = 0;

	}

	public Set(E[] entries) {
		
		setar = (E[])new Object[entries.length]; 
		size= 0;	
		for(int i = 0; i< entries.length; i++)
		{
			add(entries[i])	;
		}
		
	}

	
	
	
	public int getSize() {
		// TODO Auto-generated method stub

		return size;
	}


	public boolean isEmpty() {
		if (size == 0)
			return true;
		else
			return false;
	}


	public E remove(E entry) throws NullPointerException {
		 E removed;
		
		if(entry == null)
			throw new NullPointerException();
		
		for(int i = 0; i< size; i++)
		{
			
			if (setar[i].equals(entry))
			{
				swap(i,size-1);
				
				removed =  setar[size-1];
				setar[size-1] = null;
				size--;
				return removed;
			}
					
		}
		return null;
		
		

	}

	
	public E remove() {
		E removed = setar[size-1];
		setar[size-1] = null;
		size--;
		return removed;
		
	}


	public void clear() {
		for (int i = 0; i < setar.length; i++)
			setar[i] = null;
	
		size=0;

	}


	public boolean contains(Object entry) throws NullPointerException {
		if(entry == null)
			throw new NullPointerException();
		
		for (int i = 0; i < size; i++) {
			if (setar[i].equals(entry))// ????
				return true;

		}
		return false;
	}


	public Object[] toArray() {
		E[] oar = (E[])new Object[size];
		for(int i = 0; i < size; i++)
			oar[i] =  setar[i];
		
		return oar;
		
	}
	
	private void swap(int x, int y)
	{
		E temp = setar[y];
		setar[y] = setar[x];
		setar[x] = temp;	
		
	}


	public boolean add(E newEntry) throws NullPointerException {
		boolean equal = false;
		if(newEntry == null)
			throw new NullPointerException();
		else
		{
			for(int i = 0; i < size; i++)
				if(setar[i].equals(newEntry))
					return false;
			
			
			if(size >= setar.length)
			{
				E[] larger  = (E[])new Object[setar.length *2];
				//setar= (E[]) new Object[setar.length *
				for(int i = 0; i < size; i++)
					larger[i] = (E) setar[i];
				
				setar = larger;
				
			}
			setar[size] = newEntry;
			size++;
			return true;
			
		}
			
	}
	
	

	/*
	 * You must include a constructor public Set(int capacity) that initializes the
	 * array to the specified initial capacity, constructor public Set() that uses a
	 * reasonable default initial capacity . Finally, you should provide a
	 * constructor public Set(E[] entries) that initializes the set to include each
	 * of the provided entries. Note that this constructor must still create its own
	 * backing array, and not adopt the argument as a data member; it must also skip
	 * all duplicates and null values in the provided array, to satisfy the data
	 * structure’s usual assumptions. Whenever the capacity is reached, the array
	 * must resize, using the techniques discussed in lecture (i.e., you should
	 * never throw SetFullException).
	 * 
	 */

}
