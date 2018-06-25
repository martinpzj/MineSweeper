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
		boolean[][] exposed = new boolean[boardSize][boardSize] // showing or hidden
		int[][] counts = new int[boardSize][boardSize]; //number of neighbors with mines

		if(args.length > 0){
			//expect the only argument if any to be a number used to seed the random number generator for testing
			rand.setSeed(Integer.parseInt(args[0]));
		}

		//Main game loop that runs until the player wins or steps on a mine
		addMines(board);
		printBoard(board,exposed, counts);

	} //end of main

	//Method to add random mines throughout the board
	static void addMines(boolean[][] board){
		int minesPlaced = 0;
		while(minesPlaced < boardSize){
			int x = rand.nextInt(boardSize);
			int y = rand.nextInt(boardSize);
			minesPlaced++;
			for(int i = 0; i < boardSize; i++)
				board[x][y] = MINES;
		}
	}

	//Method to print the board
	static void print(boolean[][] board, boolean[][] exposed, int[][] counts){
		System.out.print(" 0123456789");
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
		System.out.print(" 0123456789");
		System.out.println();
	}

}// end of MineSweeper

















