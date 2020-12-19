
import java.util.List;
import java.util.Stack;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Sudoku {

 
     //coordinate
    
    static Stack<String> added = new Stack<String>();
    
    static boolean[][] given = new boolean [9][9];
    static Stack<Integer> allowed = new Stack<Integer>();

    private static boolean allTrue(boolean [] b)
    {
        for(int i = 0; i < b.length; i++)
            if(!b[i])
            {
            	return false;
                
            }


        return true;
    }

   
	private static void allowCreate(boolean[] a, boolean[] b, boolean[] c)
    { 
        for(int i = 0; i < a.length; i ++)
        {
            if(!a[i] && !b[i] && !c[i])
            {
            	System.out.println(i+1);
            	allowed.push(new Integer(i+1));
            }      
        }
    }

    static boolean isFullSolution(int[][] board) {

        
        for(int i = 0; i < 9; i ++)//is it full
        {
            for(int j = 0; j < 9; j++)
            {
                if(board[i][j] == 0)
                {
                	return false;
                }
            }
        }

        for(int[] x: board )//rows
        {
            boolean[] check = new boolean[9];
            for(int i = 0; i < 9; i++)
            {
                int temp = x[i];
                if(check[temp-1])
                {
                	return false;
                    
                }
                else
                    check[temp-1] = true;
            }

            if(!allTrue(check))
            {
          	return false;
              
          }

        }
        for( int j = 0; j < 9; j++)
        {
             boolean[] check = new boolean[9];
            for(int i = 0; i < 9; i++)
            {
                int temp = board[i][j];
                if(check[temp-1])
                {
              	return false;
                  
              }
                else
                    check[temp-1] = true;
            }
            if(!allTrue(check))
            {
          	return false;
              
          }
        }

          boolean[] check = new boolean[9];
        int s = 0, e=3;//check
        while(s<6 && e<9)
        {
        	check = new boolean[9];
            for(int i = s; i < e; i++){
                for(int j = s; j < e; j++){
                    int temp = board[i][j];
                   
                    
                    if(check[temp-1])                    {
                    	
                    	return false;
                      
                    }
                    else
                        check[temp-1] = true;
                 }
            }
            
            if(!allTrue(check))
            {
	          	System.out.println("zero6"+ " " + s + e);
	          	return false;
	              
          }
                
            

            s+=3;
            e+=3;
        }

        return true;

    }

    static boolean reject(int[][] board) {
        // TODO: Complete this method

        int x, y;
        x = Integer.parseInt(added.peek().charAt(0) + "");
        y = Integer.parseInt(added.peek().charAt(1)+ "");

        boolean[] check = new boolean[9];

        for(int i = 0; i < 9; i++)//row of previously added
        {
                int temp = board[x][i];
                if(temp == 0)
                	x = x;
                else if(check[temp -1])
                {
                	System.out.println("1");
                	return true;
                }
                else
                    check[temp -1] = true;
        }

        check = new boolean[9];
        for(int i = 0; i < 9; i++)//columns
        {
                int temp = board[i][y];
                if(temp == 0)
                    x=x;//honestly idk
                else if(check[temp -1])
                {
                	System.out.println("2");
                	return true;
                }
                else
                    check[temp-1] = true;
        }

//////////boundssssss
//checking the square
        check = new boolean[9];
        int s = x/3 *3, e=y/3*3;
        for(int i =s; i < e; i++)
        {
            for(int j = s; j< e; j++)
            {
                 int temp = board[i][y];
                if(temp == 0)
                    x=x; //honestly idk
                else if(check[temp-1])
                {
                	System.out.println("3");
                	return true;
                }
                else
                    check[temp-1] = true;
            }
        } 
        
        return false;
    }

    static int[][] extend(int[][] board) {

        int x = 10, y = 10;
        int count = 1;
        boolean found = false;
        for(int i =0; i < 9; i++) //finding next 0
        {
            for(int j = 0; j < 9; j++)
            {
                if(board[i][j] == 0)
                {
                    x = i;
                    y = j;
                    found = true;
                    break;
                }
              
                
                count++;
               
            }
            if(found)
            	break;
        }
        
        if(!found) 
        {
        	System.out.println("none found");
        	return null;
        }
         //assume we found a zero

        boolean[] check = new boolean[9];
        for(int i = 0; i < 9; i++)
        {
            int temp = board[x][i];
            if(temp!=0)
            	check[temp-1] = true;
        }

        boolean[] check1 = new boolean[9];
        for(int i = 0; i < 9; i++)
        {
            int temp = board[i][y];
            if(temp!=0)
            	check1[temp-1] = true;
        }


        boolean[] check2 = new boolean[9];
        
        int s = x/3 * 3;//check
        int sy = y/3 *3, e = s+3, ey = sy+3;
       
        
        for(int i = s; i < e; i++)
        {
            for(int j = sy; j < ey; j++)
            {
            	
                int temp = board[i][j];
                if(temp!=0)
                	check2[temp-1] = true;
            }
        }
        

        allowCreate(check, check1, check2);
        if(allowed.isEmpty())
        {
        	System.out.println("something went wrong.......oops");
            return null;
        }//well then oops
        board[x][y] = allowed.pop();
        added.push("" + x + y);
        return board;
    }

    static int[][] next(int[][] board) {
    	String temp;
    	int x, y;
    	
    	temp =added.peek();//most recently added SPOT
    	System.out.println("temp = " + temp);
		x = Integer.parseInt("" + temp.charAt(0)); //make x and y equal to most recent added SPOT
		y = Integer.parseInt("" + temp.charAt(1));
		System.out.println(x + " " + y);
		
    	if(!allowed.isEmpty())
		{
    		board[x][y] = allowed.pop(); //set most recent spot with the next allowed number
    		
    		return board;
		}
    	else//no more options on that spot so... go back to a spot
    	{
    		System.out.println("allowed empty");
    		board[x][y] = 0;//that spot is now zero
    		added.pop();//that spot was never touched....
    		
    		if(added.isEmpty())
			{
				System.out.println("empty");

    			return null;
			}
    		temp =added.peek();//ok next spot 
    		x = Integer.parseInt("" + temp.charAt(0));
    		y = Integer.parseInt("" + temp.charAt(1));
    		
    		board[x][y] = 0; //we make you zero and now we test you
    		
    		return Sudoku.extend(board);
    	}
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static void testIsFullSolution() {
    	int [][] tempBoard =
    	{
    	{2,9,5,7,4,3,8,6,1},
    	{4,3,1,8,6,5,9,2,7},
    	{8,7,6,1,9,2,5,4,3},
    	{3,8,7,4,5,9,2,1,6},
    	{6,1,2,3,8,7,4,9,5},
    	{5,4,9,2,1,6,7,3,8},
    	{7,6,3,5,2,4,1,8,9},
    	{9,2,8,6,7,1,3,5,4},
    	{1,5,4,9,3,8,6,7,2}
    	};
    	
    	System.out.println(Sudoku.isFullSolution(tempBoard));
    	
        // TODO: Complete this method
    }

    static void testReject() {
    	
    	int [][] tempBoard =
        	{
        	{2,9,5,7,4,3,8,6,1},
        	{4,3,1,8,6,5,9,2,7},
        	{8,7,6,1,9,2,5,4,3},
        	{3,8,7,4,5,9,2,1,6},
        	{6,1,2,3,8,7,4,9,5},
        	{5,4,9,2,1,6,7,3,8},
        	{7,6,3,5,2,4,1,8,9},
        	{9,2,8,6,7,1,3,5,4},
        	{1,5,4,9,3,8,6,7,2}
        	};
    	
    		added.push("44");
        	
        	System.out.println(Sudoku.reject(tempBoard));
    	
    	
        // TODO: Complete this method
    }
    static void testExtend() {
        // TODO: Complete this method
    	
    	int [][] tempBoard =
        	{
        	{2,9,5,7,4,3,8,6,1},
        	{4,3,1,8,6,5,9,2,7},
        	{8,7,6,1,9,2,5,4,3},
        	{3,8,7,4,5,9,2,1,6},
        	{6,1,2,3,8,7,4,9,5},
        	{5,4,9,2,1,6,7,3,8},
        	{7,6,3,5,2,4,1,8,9},
        	{9,2,8,6,7,1,3,5,4},
        	{1,5,4,9,3,8,6,7,2}
        	};
        	
        	Sudoku.printBoard(Sudoku.extend(tempBoard));
    }

    static void testNext() {
        // TODO: Complete this method
    	
    	
    	int [][] tempBoard =
        	{
        	{2,9,5,7,4,3,8,6,1},
        	{4,3,0,8,6,5,9,2,7},
        	{8,0,6,0,0,2,5,4,3},
        	{3,8,7,4,5,9,2,1,6},
        	{6,0,2,3,8,7,4,9,5},
        	{5,4,9,2,1,6,7,3,8},
        	{7,0,3,5,2,4,1,8,9},
        	{9,2,8,6,7,1,3,5,4},
        	{1,5,4,9,3,8,6,7,2}
        	};
    	
    		added.push("11");
    		allowed.push(new Integer(1));
    		allowed.push(new Integer(2));
    		allowed.push(new Integer(3));
    		allowed.push(new Integer(4));
    		allowed.push(new Integer(5));
        	
        	Sudoku.printBoard(Sudoku.next(tempBoard));
    	
    }

    static void printBoard(int[][] board) {
        if (board == null) {
            System.out.println("No assignment");
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                System.out.println("----+-----+----");
            }
            for (int j = 0; j < 9; j++) {
                if (j == 2 || j == 5) {
                    System.out.print(board[i][j] + " | ");
                } else {
                    System.out.print(board[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    static int[][] readBoard(String filename) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
        } catch (IOException e) {
            return null;
        }
        int[][] board = new int[9][9];
        int val = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
                } catch (Exception e) {
                    val = 0;
                }
                if(val == 0)
                    given[i][j] = true;
                board[i][j] = val;
            }
        }
        return board;
    }

    static int[][] solve(int[][] board) {
        if (reject(board)) //board is bad from start
        	return null;
        if (isFullSolution(board))//board is done
        	return board;
        int[][] attempt = extend(board);//lets try one 
        while (attempt != null) 
        {
            int[][] solution = solve(attempt);
            if (solution != null)
            	return solution;
            attempt = next(attempt);
        }
        return null;
    }

    public static void main(String[] args) {
       if (args[0].equals("-t")) {
            testIsFullSolution();
            testReject();
            testExtend();
            testNext();
        } else {
            int[][] board = readBoard(args[0]);
            printBoard(board);
            System.out.println("Solution:");
            printBoard(solve(board));
        }
        
       
    	
    }
}

