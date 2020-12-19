package cs445.a1;

public class TestingClass {
	
	public static void main(String args[])
	{/*
		GroceryItem ga = new GroceryItem("a", 2);
		GroceryItem gb = new GroceryItem("b", 4);
		GroceryItem gc = new GroceryItem("c", 6);
		
		GroceryItem gd = new GroceryItem("d", 8);
		
		GroceryItem ge = new GroceryItem("d", 20);
		
		Groceries groItems = new Groceries();
		Set<String> fruits = new Set<String>(5);
		
		fruits.add("apple");
		fruits.add("banana");
		fruits.add("cantaloupe");
		fruits.add("dates");
		fruits.add("banana");
		fruits.add("grape");
		fruits.add("happy");
		
		String[] fruit = {"apple", "banana", "cantaloupe", "dates", "banana", "grape"};
		
		Set<String> fr = new Set<String>(fruit);
		System.out.println(fr.getSize());
		
		groItems.addItem(ga);
		groItems.addItem(gb);
		groItems.addItem(gc);
		groItems.addItem(gd);
	
		groItems.printAll();
		*/
		 // Create my grocery list
        GroceriesInterface billsGroceries = new Groceries();

        // Add dinner
        billsGroceries.addItem(new GroceryItem("Pork roast", 1));
        billsGroceries.addItem(new GroceryItem("Fennel", 2));
        billsGroceries.addItem(new GroceryItem("White wine", 1));
        billsGroceries.addItem(new GroceryItem("Rosemary", 1));
        billsGroceries.addItem(new GroceryItem("Fuji apple", 3));
        billsGroceries.addItem(new GroceryItem("Red onion", 1));

        // Print groceries so far
        billsGroceries.printAll();
        System.out.println();

        // Just found out friends are coming over! Better double the meat...
        billsGroceries.modifyQuantity(new GroceryItem("Pork roast", 2));

        // ... and add another bottle of wine...
        billsGroceries.addItem(new GroceryItem("White wine", 1));

        // ... and they're bringing the apples.
        billsGroceries.removeItem(new GroceryItem("Fuji apple", 2));

        // Make sure pork and wine are listed once each!
        billsGroceries.printAll();

		
		
		
		
		
		
		
		
		
		
		//fruits.printAll();
		//System.out.println(fruits.setar.length);
		//fr.printAll();
		//fruits.clear();
		//fruits.printAll();
		
		
		
	}

}
