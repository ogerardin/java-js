import java.io.*;

/**
 *	Console interface for the TicTacToe game
 */
public class ConsoleTTT{
	
	/** A reference to the TicTacToe game */
	private TicTacToe ttt;
	
	/**
	 *	Main program entry
	 *	@args	No external arguments required
	 */
	public static void main(String args[]) {
		new ConsoleTTT().consoleGameStart();
	}
	
	/**
	 *	Default constructor. Initialize the game.
	 */
	public ConsoleTTT(){
		ttt = new TicTacToe();
	}

	/**
	 *	Starts the console game. Repeats the cycle of board display and
	 *	get board input until either either player wins or the game draws.
	 */
	private void consoleGameStart(){
		consoleGetOptions();
		
		//diplay the board
		consoleBoardDisplay();
		do{	
			//either player makes a move		
			if (ttt.isPlayerTurn()) consoleBoardInput();
			else ttt.computeMove();
			consoleBoardDisplay();
			
			//check for winning states and draw states
			if (ttt.checkWin()){
				if (ttt.isPlayerTurn()) System.out.println("Computer Wins");
				else System.out.println("Player Wins");
				break;
			}
			if (ttt.checkDraw()){
				System.out.println("Its a Draw Game");
				break;
			}
		}while (true);
	}
	
	/**
	 *	Displays a the game board in the console (ASCII)
	 */
	public void consoleBoardDisplay(){
		String cell[][] = ttt.getCells();
		System.out.println("\n" +
			"R0:  " + cell[0][0] + '|' + cell[0][1] + '|' + cell[0][2] + "\n" +
			"     " + '-'  + '+' + '-'  + '+' + '-'  + "\n" +
			"R1:  " + cell[1][0] + '|' + cell[1][1] + '|' + cell[1][2] + "\n" +
			"     " + '-'  + '+' + '-'  + '+' + '-'  + "\n" +
			"R2:  " + cell[2][0] + '|' + cell[2][1] + '|' + cell[2][2] + "\n"
		);
	}
	
	/**
	 *	Prompts the player to input values for row and columns
	 */
	public void consoleBoardInput(){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String input;
		int row=0,col=0;
		
		System.out.println("Current symbol: " + ttt.getCurrentSymbol());
		
		//get row
		do{
			try{
				System.out.print("Enter Row: ");
				input=in.readLine();
				row = Integer.parseInt(input);
				if (row<0 || row>2) throw new IllegalArgumentException();
				break;
			}
			catch(Exception e){
				System.out.println("Invalid input");
			}
		}while(true);
		
		//get column
		do{
			try{
				System.out.print("Enter Col: ");
				input=in.readLine();
				col = Integer.parseInt(input);
				if (col<0 || col>2) throw new IllegalArgumentException();
				break;
			}
			catch(Exception e){
				System.out.println("Invalid input");
			}
		}while(true);
		
		//check whether selected cell is valid
		if (!ttt.placeMove(row,col))
			System.out.println("Selected cell already filled. Try again.");
	}
	
	/**
	 *	Prompts the player to input game order and difficulty.
	 */
	public void consoleGetOptions(){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String input;
		int diff=0,first=0;
		do{
			try{
				System.out.print("Who starts first (0-Player, 1-Computer): ");
				input=in.readLine();
				first = Integer.parseInt(input);
				if (first<0 || first>1) throw new IllegalArgumentException();
				if (first==1) ttt.switchPlayer();
				break;
			}
			catch(Exception e){
				System.out.println("Invalid input");
			}
		}while(true);		

		do{
			try{
				System.out.print("Enter AI level (0-Noob, 1-Normal, 2-God Mode): ");
				input=in.readLine();
				diff = Integer.parseInt(input);
				if (diff<0 || diff>2) throw new IllegalArgumentException();
				ttt.setDifficulty(diff);
				break;
			}
			catch(Exception e){
				System.out.println("Invalid input");
			}
		}while(true);		
	}
}