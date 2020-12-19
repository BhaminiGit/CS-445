package cs445.a3

import java.util.List;
import java.util.Stack;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Sudoku {

 
     //coordinate
    
    public static int nextCount = 0;
    static int onlyFrom;
    
    static Stack<String> added = new Stack<String>();
    
    static boolean[][] given = new boolean [9][9];
    static Stack<Integer> allowed = new Stack<Integer>();
    static Stack<Integer> allowedNext = new Stack<Integer>();


    private static boolean allTrue(boolean [] b)
    {
        for(int i = 0; i < b.length; i++)
            if(!b[i])
                return false;

        return true;
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
              
                return false;
                  
          }
                
            

            s+=3;
            e+=3;
        }

        return true;

    }

    static boolean reject(int[][] board) {
        // TODO: Complete this method

       
          
         for(int[] x: board )//rows
         {
             boolean[] check = new boolean[9];
             for(int i = 0; i < 9; i++)
             {
                 int temp = x[i];
                 if(temp!=0)
                 {
                     if(check[temp-1])
                     {
                         return true;
                        
                     }
                     
                     else
                         check[temp-1] = true;
                 }
             }

         }
         for( int j = 0; j < 9; j++)
         {
              boolean[] check = new boolean[9];
             for(int i = 0; i < 9; i++)
             {
                 int temp = board[i][j];
                 if(temp!=0)
                 {
                     if(check[temp-1])
                     {
    
                            return true;
                       
                     }
                     else
                         check[temp-1] = true;
                }
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
                    
                     if(temp!=0)
                     {
                         if(check[temp-1])                    {
                            

                             return true;
                            
                           
                         }
                         else
                             check[temp-1] = true;
                     }
                  }
             }
             
             s+=3;
             e+=3;
         }

         return false;

    }

    static int[][] extend(int[][] board) {

        int x = 10, y = 10;
        
        int[][] newBoard = new int[9][9];
        for(int i = 0; i < 9; i++)
        {
            newBoard[i] = board[i].clone();
        }
    
        boolean found = false;
        for(int i =0; i < 9; i++) //finding next 0
        {
            for(int j = 0; j < 9; j++)
            {
                if(newBoard[i][j] == 0)
                {
                    x = i;
                    y = j;
                    found = true;
                    break;
                }
            }
            if(found)
                break;
        }
        
        if(!found) 
            return null;
         //assume we found a zero
     
        
       newBoard[x][y] = 1;
       added.push(x +"" + y);
        return newBoard;
    }

    static int[][] next(int[][] board) {
        
        String tempPoint;
        int x, y;
        
        if(added.isEmpty())
        {
            return null;
        }
        tempPoint =added.peek();//most recently added SPOT
        x = Integer.parseInt("" + tempPoint.charAt(0)); //make x and y equal to most recent added SPOT
        y = Integer.parseInt("" + tempPoint.charAt(1));
        
        while(board[x][y] == 9)
        {
            board[x][y] = 0;
            added.pop(); 
            tempPoint = added.peek();
            x = Integer.parseInt("" + tempPoint.charAt(0));
            y = Integer.parseInt("" + tempPoint.charAt(1));
            
        }

        board[x][y] += 1;

        return board;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  

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
                board[i][j] = val;
            }
        }
        return board;
    }

    static int[][] solve(int[][] board) {
        if (reject(board)) return null;
        if (isFullSolution(board)) return board;
        int[][] attempt = extend(board);
        while (attempt != null) {
            int[][] solution = solve(attempt);
            if (solution != null) return solution;
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
    
    static void testIsFullSolution() {
        
        //correct
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
        
        //not full causse zero
        int [][] tempBoard1 =
            {
            {2,9,5,7,0,3,8,6,1},
            {4,3,1,8,6,5,9,2,7},
            {8,7,6,1,9,2,5,4,3},
            {3,8,7,4,5,9,2,1,6},
            {6,1,2,3,8,7,4,9,5},
            {5,4,9,2,1,6,7,3,8},
            {7,6,3,5,2,4,1,8,9},
            {9,2,8,6,7,1,3,5,4},
            {1,5,4,9,3,8,6,7,2}
            };
        
        
        //not correct cause two of same numbers
        int [][] tempBoard2 =
            {
            {2,9,5,5,4,3,8,6,1},
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
        System.out.println(Sudoku.isFullSolution(tempBoard1));
        System.out.println(Sudoku.isFullSolution(tempBoard2));
        
        // TODO: Complete this method
    }

    static void testReject() {
        
        //correct
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
            
        //still correct
        int [][] tempBoard1 =
            {
                {2,9,5,0,4,3,8,6,1},
                {4,0,1,8,6,5,9,2,7},
                {8,7,6,1,9,2,0,4,3},
                {3,8,7,4,0,9,2,1,6},
                {0,1,2,3,8,7,0,9,5},
                {5,4,9,2,1,6,7,3,8},
                {7,6,3,5,2,4,1,8,9},
                {9,2,8,6,7,1,3,5,4},
                {1,5,4,9,3,8,6,7,2}
            };
        
        //not correct
        int [][] tempBoard2 =
            {
                {2,9,5,7,4,3,8,6,1},
                {4,3,1,8,6,5,9,2,7},
                {8,7,6,1,9,2,5,4,3},
                {3,8,7,4,5,9,2,1,6},
                {6,1,2,3,8,7,4,9,5},
                {5,4,9,2,6,6,7,3,8},
                {7,6,3,5,2,4,1,8,9},
                {9,2,8,6,7,1,3,5,4},
                {1,5,4,9,3,8,6,7,2}
            };
        
        //not correct
        int [][] tempBoard3 =
            {
                {2,9,5,0,2,3,8,6,1},
                {4,0,1,8,6,5,9,2,7},
                {8,7,6,1,9,2,0,4,3},
                {3,8,7,4,0,9,2,1,6},
                {0,1,2,3,8,7,0,9,5},
                {5,4,9,2,1,6,7,3,8},
                {7,6,3,5,2,4,1,8,9},
                {9,2,8,6,7,1,3,5,4},
                {1,5,4,9,3,8,6,7,2}
            };
            
            
            System.out.println(Sudoku.reject(tempBoard));
            System.out.println(Sudoku.reject(tempBoard1));
            System.out.println(Sudoku.reject(tempBoard2));
            System.out.println(Sudoku.reject(tempBoard3));
        
        
        // TODO: Complete this method
    }
    static void testExtend() {
        // TODO: Complete this method
        
        int [][] tempBoard =
            {
            {2,9,5,7,4,3,8,6,1},  //should fill that spot
            {4,3,1,8,6,5,9,2,7},
            {8,7,6,1,9,2,5,4,3},
            {3,8,7,4,0,9,2,1,6},//fill spot
            {6,1,2,3,8,7,4,9,5},
            {5,4,9,2,1,6,7,3,8},
            {7,6,3,5,2,4,1,8,9},
            {9,2,8,6,7,1,3,5,4},
            {1,5,4,9,3,8,6,7,2}
            };
        
        int [][] tempBoard1 =
            {
            {2,0,5,0,4,3,8,6,1},  //should fill that spot
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
        
        //coordinates
            added.push("11");//isnt a nine so good
            Sudoku.printBoard(Sudoku.next(tempBoard));
            added.clear();

            
            int [][] tempBoard1 =
                {
                {2,9,5,7,4,3,8,6,1},
                {4,3,0,8,6,5,9,2,7},
                {8,0,6,0,0,2,5,4,3},
                {3,8,7,9,5,9,2,1,6},
                {6,0,2,3,8,7,4,9,5},
                {5,4,9,2,1,6,7,3,8},
                {7,0,3,5,2,4,1,8,9},
                {9,2,8,6,7,1,3,5,4},
                {1,5,4,9,3,8,6,7,2}
                };
            
            //coordinates
                added.push("11");
                added.push("22");//not a nine so good, add 1
                added.push("33");//is a nine, so change to zero             
                Sudoku.printBoard(Sudoku.next(tempBoard1));
                added.clear();
        
                int [][] tempBoard2 =
                    {
                    {2,9,5,7,4,3,8,6,1},
                    {4,3,0,8,6,5,9,2,7},
                    {8,0,9,0,0,2,5,4,3},
                    {3,8,7,9,5,9,2,1,6},
                    {6,0,2,3,8,7,4,9,5},
                    {5,4,9,2,1,6,7,3,8},
                    {7,0,3,5,2,4,1,8,9},
                    {9,2,8,6,7,1,3,5,4},
                    {1,5,4,9,3,8,6,7,2}
                    };
                
                //coordinates
                    added.push("11");//not a nine, so ++
                    added.push("22");//is a nine, so become zero
                    added.push("33");//is a nine, so change to zero             
                    Sudoku.printBoard(Sudoku.next(tempBoard2));
                    added.clear();
            
                    
    }
}

