package cs445.a1;


/*
 *Develop the Groceries class, an implementation of the ADT described in GroceriesInterface. 
 *Include this class in package cs445.a1. 
 *Read the interface carefully (including comments) to ensure you implement it properly. 
 *As with Set, it will be graded using a client that expects the full functionality described in its interface. 
 *
 *The Groceries class can be seen as a client of the Set data structure. 
 *
 *Use composition with your Set<E> class to storethe items as a data member of type Set<GroceryItem>. 
 *Include a constructor public Groceries() that initializes the set.
 *
 *Method Points Groceries() 3 
 *
 *void addItem(GroceryItem) 7 
 *void removeItem(GroceryItem) 7 
 *int modifyQuantity(GroceryItem) 9 
 *void printAll() 9

 */

public class Groceries implements GroceriesInterface {
	
	private Set<GroceryItem> items; 
	
	public Groceries() {
		items = new Set<GroceryItem>();
	}
	
	 /**
     * Adds item to these groceries. If an item with the same description is
     * already in these groceries, then its quantity is increased by the amount
     * specified. For instance, if 2 bananas are added when 3 bananas are in the
     * groceries, the quantity is increased to 5 bananas. If an item with the
     * same description is not in these groceries, then it is added with the
     * specified quantity.
     *
     * @param item the item to add
     */

	@Override
	public void addItem(GroceryItem item) {
		
		GroceryItem temp = null;
		
		if(items.contains(item))
		{
			temp = (GroceryItem) items.remove(item);
			items.add(new GroceryItem(temp.getDescription(), item.getQuantity() + temp.getQuantity()));
		
		}
		else
		{
			items.add(item);
		}
		
		// TODO Auto-generated method stub
		
	}
	

    /**
     * Removes item from these groceries. If an item with the same description
     * is in these groceries, then its quantity is decreased by the amount
     * specified. For instance, if 3 bananas are removed when 10 bananas are in
     * the groceries, the quantity is decreased to 7 bananas. If the specified
     * quantity is greater than or equal to the current quantity, the item is
     * removed entirely. If an item with the -same description is not in these
     * groceries, this method does nothing.
     *
     * @param item the item to remove
     */

	@Override
	public void removeItem(GroceryItem item) {
		GroceryItem temp = (GroceryItem) items.remove(item);
		if(temp != null)
		{
			if(item.getQuantity() < temp.getQuantity())
				items.add(new GroceryItem(item.getDescription(), temp.getQuantity() - item.getQuantity()));
		}
		// TODO Auto-generated method stub
		
	}
	
	
	/**
     * Modifies an item's quantity within these groceries. If an item with the
     * same decsription is in these groceries, then its quantity is modified to
     * match the amount specified, and the previous quantity is returned. If an
     * item with the same description is not in these groceries, this method
     * makes no changes and returns -1.
     *
     * @param item the item to modify in the set
     * @return  the old quantity, or -1 if the item was not found
     */

	@Override
	public int modifyQuantity(GroceryItem item) {
		
		GroceryItem temp;
		int prev;
		// TODO Auto-generated method stub
		if(items.contains(item))
		{
			temp = (GroceryItem) items.remove(item);
			prev = temp.getQuantity();
			items.add(new GroceryItem(temp.getDescription(), item.getQuantity()));
			return prev;
		}
		else
			return -1;
			
	}

	@Override
	public void printAll() {
	
		System.out.println("Groceries");
		Object[] g = items.toArray();
		
		for(int i = 0; i < g.length;i++)
			System.out.println(g[i]);
		// TODO Auto-generated method stub
		
	}

}
