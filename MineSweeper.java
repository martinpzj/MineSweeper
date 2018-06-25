/**
 * CREATED BY: Martin Perez
 * A program to play MineSweeper
 */
import java.util.*;

class MineSweeper{
    static final int boardSize = 10;
    static final Random rand = new Random();
    static final boolean MINE = true;
    static final boolean CLEAR = false;

	public static void main(String[] args){
	    Scanner input = new Scanner(System.in);
	    boolean[][] board = new boolean[boardSize][boardSize]; // mines or no mines
	    boolean[][] exposed = new boolean[boardSize][boardSize]; // showing or hidden
	    int[][] counts = new int[boardSize][boardSize]; //number of neighbors with mines

	     if(args.length > 0){
	         //expect the only argument if any to be a number used to seed the random number generator for testing
		 rand.setSeed(Integer.parseInt(args[0]));
	     }

	     addMines(board);
	     printBoard(board,exposed, counts);
	     System.out.print("Enter two integers(row and column): ");
	     //Main game loop that runs until the player wins or steps on a mine
	     while(input.hasNextInt()){
		 int i = input.nextInt();
		 int j = input.nextInt();
		 countsNeighbor(counts, board);
		 expose(i,j,board,exposed,counts);
		 if(countExposed(exposed) + countMines(board) == 100){
		     System.out.println("You win!");
		     revealBoard(board,counts);
		     return;
		 }
		 if(board[i][j] == MINE){
		     System.out.println("GAME OVER!");
		     revealBoard(board, counts);
		     return;
		 }
		else{
		    printBoard(board,exposed,counts);
		    System.out.print("Enter two integers(row and column): ");
		}
	     }

	} //end of main

	//Method to add random mines throughout the board
	static void addMines(boolean[][] board){
	    int minesPlaced = 0;
	    while(minesPlaced < boardSize){
		int x = rand.nextInt(boardSize);
		int y = rand.nextInt(boardSize);
		minesPlaced++;
		for(int i = 0; i < boardSize; i++)
		    board[x][y] = MINE;
	    }
	}

	//Method to print the board
	static void printBoard(boolean[][] board, boolean[][] exposed, int[][] counts){
	    System.out.print("  0123456789");
	    for(int i = 0; i < board.length; i++){
		System.out.println();
		System.out.print(i);
		System.out.print(" ");
		for(int j = 0; j < board.length; j++){
		    if(exposed[i][j])
			System.out.print(counts[i][j]);
		    else
			System.out.print(".");
		}
		System.out.print(" " + i);
	     }
	     System.out.println();
	     System.out.print("  0123456789");
	     System.out.println();
	}

	//Method to expose the specified location
	static void expose(int r, int c, boolean[][] board, boolean[][] exposed, int[][] counts){
	    if(exposed[r][c])
		return;	//nothing to do
	    //expose any neighbors that have zero counts
	    exposed[r][c] = true;
	    if(counts[r][c] > 0)
		return;
	    for(int i = -1; i <= 1; i++){
		for(int j = -1; j <= 1; j++){
		    int x = r+i;
		    int y = c+j;
		    if( !(i==0 && j==0) && x >= 0 && x < board.length && y >= 0 && y < board[x].length){
			if(counts[x][y] == 0)
			    expose(x,y,board,exposed,counts);
			else
			    exposed[x][y] = true; //just expose the boarder region
		    }
		}
	    }
	}

	//Count the number of mines around the board and store info into counts
	static void countsNeighbor(int[][] counts, boolean[][] board){
		for(int i = 0; i < boardSize; i++){
		    for(int j = 0; j < boardSize; j++)
			counts[i][j] = countEm(i,j,board);
		}
	}

	//Method that checks a 3x3 area for mines
	static int countEm(int i, int j, boolean[][] board){
	    int x = 0;
	    if( i-1 >= 0 && j -1 >= 0 && i-1 < boardSize && j-1 < boardSize && board[i-1][j-1] ==MINE)
		x++;
    	    if( i-1 >= 0 && j >= 0 && i-1 < boardSize && j < boardSize && board[i-1][j] ==MINE)
      		x++;
   	    if( i -1 >= 0 && j +1 >= 0 && i -1 < boardSize && j+1 < boardSize && board[i-1][j+1] ==MINE)
      		x++;
    	    if( i >= 0 && j -1 >= 0 && i < boardSize && j-1 < boardSize && board[i][j-1] ==MINE)
      		x++;
    	    if( i >= 0 && j +1 >= 0 && i < boardSize && j+1 < boardSize && board[i][j+1] ==MINE)
      		x++;
    	    if( i+1 >= 0 && j- 1>= 0 && i+1 < boardSize && j -1 < boardSize && board[i+1][j-1] ==MINE)
      		x++;
    	    if( i+1 >= 0 && j >= 0 && i+1 < boardSize && j < boardSize && board[i+1][j] ==MINE)
      		x++;
    	    if( i+1 >= 0 && j + 1>= 0 && i+1 < boardSize && j+1 < boardSize && board[i+1][j+1] ==MINE)
      		x++;
    
    	    return x;
	}

	//Method that reveals where mines are located after winning or losing
	static void revealBoard(boolean[][] board, int[][] counts){
	    System.out.print("  0123456789");
	    for(int i = 0; i < board.length; i++){
		System.out.println();
		System.out.print(i);
		System.out.print(" ");
		for(int j = 0; j < board.length; j++){
		    if(board[i][j] == MINE)
			System.out.print("*");
		    else
			System.out.print(counts[i][j]);
		}
		System.out.print(" " + i);
	     }
	     System.out.println();
	     System.out.print("  0123456789");
	     System.out.println();
	}

	//Method used to calculate amount of exposed cells
	static int countExposed(boolean[][] exposed){
	    int c = 0;
	    for(int i = 0; i < boardSize; i++){
		for(int j = 0; j < boardSize; j++){
		    if(exposed[i][j])
			c++;
		}
	    }
	    return c;
	}

	//Method to calculate amount of mines
	static int countMines(boolean[][] board){
	    int x = 0;
	    for(int i = 0; i < board.length; i++){
		for(int j = 0; j < board.length; j++){
		    if(board[i][j] == MINE)
			x++;
		}
	    }
	    return x;
	}
}// end of MineSweeper
