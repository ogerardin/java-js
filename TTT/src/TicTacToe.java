import java.util.Random;
import java.lang.Math;

/**
 *	TicTacToe main game class.
 *	(In compliant with MIDP 2.0 and CLDC 1.0)
 */
public class TicTacToe{

	/** Whether it is the current player's turn or opposing player/computer. */
	private boolean playerTurn;
	
	/** What is the current symbol to draw, either "O" or "X". */
	private String currentSymbol;

	/** A 3x3 cell grid to map the playing board (3 rows, 3 columns). */
	private String cell[][];
	
	/** Determines the level of computation to be employed by the computer. */
	private int difficulty;

	/** A buffer variable to store the last pattern used in strategyPattern. */
	private String lastPattern;
	
	/** Random object used to make random choices. */
	Random rnd = new Random(System.currentTimeMillis());


	//--------------------------------------------------------------------------
	//	Constructor(s)
	//--------------------------------------------------------------------------
	
	/**
	 *	Default constructor, calls gameReset() during initialization.
	 */
	public TicTacToe(){
		cell = new String[3][3];		
		setDifficulty(0);
		gameReset();
	}
	

	//--------------------------------------------------------------------------
	//	Accessor(s) and Mutator(s)
	//--------------------------------------------------------------------------

	/**
	 *	Get whether it is the current player's turn.
	 *	@return Whether it is the current player's turn.
	 */
	public boolean isPlayerTurn(){
		return playerTurn;
	}
	
	/**
	 *	Sets the current player's turn
	 *	@param isPlayerTurn Whether it is the current player's turn
	 */
	public void setPlayerTurn(boolean isPlayerTurn){
		playerTurn=isPlayerTurn;
	}
	
	/**
	 *	Get the entrie cell grid. This is a 2-dimensional array of String
	 *	with 3 subscript on each dimension (3x3 grid).
	 *	@return 2-dimensional String array.
	 */
	public String[][] getCells(){
		return cell;
	}

	/**
	 *	Gets the symbol for the specified cell.
	 *	@param row	Row of the cell.
	 *	@param col	Column of the cell.
	 *	@return Symbol of the specified cell.
	 */	
	public String getCellSymbol(int row, int col){
		if (row<0 || row>2 || col<0 || row>2) throw new IndexOutOfBoundsException();
		return cell[row][col];
	}

	/**
	 *	Sets a specified cell with a new symbol
	 *	@param row	Row of the cell
	 *	@param col	Column of the cell. 
	 *	@symbol	The new symbol to set. If invalid symbol, nothing is changed.
	 */
	public void setCellSymbol(int row, int col, String symbol){
		if (row<0 || row>2 || col<0 || row>2) return;
		if (symbol.equals("X") || 
			symbol.equals("O") || 
			symbol.equals(" "))
			cell[row][col]=symbol;
	}

	/**
	 *	Get the current symbol, the symbol to be drawn next onto the board.
	 *	@return The current turn's symbol.
	 */
	public String getCurrentSymbol(){
		return currentSymbol;
	}

	/**
	 *	Sets the current symbol, the symbol to be drawn next onto the board.
	 *	@param newSymbol The current turn's symbol. Either "X", "O", or " ". Default: " ".
	 */
	public void setCurrentSymbol(String newSymbol){
		if (newSymbol.equals("X") || 
			newSymbol.equals("O") || 
			newSymbol.equals(" "))
			currentSymbol = newSymbol;
		else
			currentSymbol = " ";
	}
	
	/**
	 *	Get the current game difficulty level (higher = smarter)
	 *	@return The current difficulty level
	 */
	public int getDifficulty(){
		return difficulty;
	}

	/**
	 *	Set the current game difficulty level (higher = smarter)
	 *	@param newLevel The new difficulty level (negative value not accepted)
	 */	
	public void setDifficulty(int newLevel){
		if (newLevel<0) newLevel=0;
		difficulty=newLevel;
	}


	//--------------------------------------------------------------------------
	//	Other method(s)
	//--------------------------------------------------------------------------

	/**
	 *	Overrides the Object clone() method. Makes a copy of itself.
	 *	@return A copy of this TicTacToe.
	 */
	protected TicTacToe clone(){
		TicTacToe ttt = new TicTacToe();
		ttt.setPlayerTurn(isPlayerTurn());
		ttt.setCurrentSymbol(getCurrentSymbol());
		ttt.setDifficulty(getDifficulty());
		
		for (int r=0; r<3; r++){
			for (int c=0; c<3; c++){
				ttt.setCellSymbol(r,c,getCellSymbol(r,c));
			}
		}
		return ttt;
	}
	
	/**
	 *	Resets the game to its default state. This returns the game back to 
	 *	the player's turn, the current symbol to "X", and populate all cell
	 *	values to " ". Game difficulty however is not changed.
	 */
	public void gameReset(){
		setPlayerTurn(true);
		setCurrentSymbol("X");
				
		for (int r=0; r<3; r++){
			for (int c=0; c<3; c++){
				setCellSymbol(r,c," ");
			}
		}		
	}
			
	/**
	 *	Place the current symbol onto the specified row and column in the cell.
	 *	Returns true if the move is valid, of false (if the cell is not empty).
	 *	Place move also switches the current symbol and the player's turn.
	 *	@param	row Row of the cell.
	 *	@param	col Column of the cell.
	 *	@return	Whether the move is successful.
	 */
	public boolean placeMove(int row,int col){
		if (!getCellSymbol(row,col).equals(" ")) return false;
		setCellSymbol(row,col,getCurrentSymbol());
		switchSymbol();
		switchPlayer();
		return true;
	}
	
	/**
	 *	Switches the current player's turn. Usually used at the beginning to
	 *	change starting player.
	 */
	public void switchPlayer(){
		setPlayerTurn(!isPlayerTurn());
	}
	
	/**
	 *	Switches the current symbol ("X" to "O", or vice versa). Usually used
	 *	at the beginning of the game to change starting symbol.
	 */
	public void switchSymbol(){
		if (getCurrentSymbol().equals("X")) setCurrentSymbol("O");
		else setCurrentSymbol("X");
	}
	
	/**
	 *	Check whether a winning state is achieved, that is 3 same symbol
	 *	located on the same row.
	 *	@return Whether a winning state is found.
	 */
	public boolean checkWin(){
		String symbol[] = {"X","O"};
		
		//check for both symbols
		for (int i=0; i<2; i++){
			
			//check all rows
			for (int r=0; r<3; r++){
				if (getCellSymbol(r,0).equals(symbol[i]) &&
					getCellSymbol(r,1).equals(symbol[i]) &&
					getCellSymbol(r,2).equals(symbol[i])) 
					return true;
			}
			
			//check all columns
			for (int c=0; c<3; c++){
				if (getCellSymbol(0,c).equals(symbol[i]) &&
					getCellSymbol(1,c).equals(symbol[i]) &&
					getCellSymbol(2,c).equals(symbol[i])) 
					return true;
			}
			
			//check diagonal top left to bottom right
			if (getCellSymbol(0,0).equals(symbol[i]) &&
				getCellSymbol(1,1).equals(symbol[i]) &&
				getCellSymbol(2,2).equals(symbol[i])) 
				return true;

			//check diagonal top right to bottom left
			if (getCellSymbol(0,2).equals(symbol[i]) &&
				getCellSymbol(1,1).equals(symbol[i]) &&
				getCellSymbol(2,0).equals(symbol[i])) 
				return true;
		}
		//no matches found, so return false
		return false;
	}
	
	/**
	 *	Checks whether the game has reached a draw state.
	 *	@return Whether a draw state is found.
	 */
	public boolean checkDraw(){
		//check whether all cells are filled
		for(int r=0; r<3; r++){
			for(int c=0; c<3; c++){
				if (getCellSymbol(r,c).equals(" ")) return false;
			}
		}		
		if (checkWin()) return false;
		return true;
	}
	
	/**
	 *	The computer will compute a move and calls the placeMove method.
	 */
	public void computeMove(){
		int pos[] = new int[2];

		//select level of AI strategy to employ
		switch(getDifficulty()){
		
		case 1:		//normal (rule base: know how to win or block player,)
			pos = strategyNormal();
			break;
		case 2:		//smart (analyze winning patterns on the board)
			pos = strategyPattern();
			break;
		case 0:		//noob (randomly place moves)
		default:
			pos = strategyRandom();			
		}
		placeMove(pos[0],pos[1]);
	}
	
	
	//--------------------------------------------------------------------------
	//	Method(s) used for computing AI moves
	//--------------------------------------------------------------------------	
	
	/**
	 *	Just randomly selects a non-empty cell
	 *	@return Array containing the row and column value. 0-row, 1-column
	 */
	private int[] strategyRandom(){
		int r=0,c=0;
		
		do {
			r=Math.abs(rnd.nextInt())%3;
			c=Math.abs(rnd.nextInt())%3;
		}while(!getCellSymbol(r,c).equals(" "));
		
		int randomMove[] = {r,c};
		return randomMove;
	}
	
	/**
	 *	Using normal rule of thumb knowledge. If putting in a cell will cause
	 *	the computer to win, it will do so. If putting a cell can block the
	 *	player from winning, it will do so.
	 *	@return Array containing the row and column value. 0-row, 1-column or
	 *			null if no opportunity to win or block.
	 */
	private int[] tryWinOrBlock(){
		//if computer can win, then win
		for (int r=0; r<3; r++){
			for (int c=0; c<3; c++){
				if (getCellSymbol(r,c).equals(" ")){
					TicTacToe ttt = clone();
					ttt.placeMove(r,c);
					if (ttt.checkWin()){
						int pos[] = {r,c};
						return pos;
					}
				}
			}
		}

		//if computer can block then must block
		for (int r=0; r<3; r++){
			for (int c=0; c<3; c++){
				if (getCellSymbol(r,c).equals(" ")){
					TicTacToe ttt = clone();
					ttt.switchPlayer();
					ttt.switchSymbol();
					ttt.placeMove(r,c);
					if (ttt.checkWin()){
						int pos[] = {r,c};
						return pos;
					}
				}
			}
		}
		return null;
	}

	/**
	 *	Randomly returns the position for any corner on the cell grid
	 *	@return Array containing the row and column value. 0-row, 1-column
	 */
	private int[] getRandomCorner(){
		int pos[] = new int[2];
		int corner = Math.abs(rnd.nextInt())%4;
		
		switch(corner){
		case 0:
			pos[0]=0; pos[1]=0;		//top left
			break;	
		case 1:
			pos[0]=0; pos[1]=2;		//top right
			break;	
		case 2:
			pos[0]=2; pos[1]=0;		//bottom left
			break;	
		case 3:
			pos[0]=2; pos[1]=2;		//bottom right
			break;	
		}
		return pos;
	}
		
	/**
	 *	Same as getRandomCorner, but this method returns only if its vacant
	 *	@return Array containing the row and column value. 0-row, 1-column,
	 *			returns null if all corners are not vacant
	 */
	private int[] getEmptyRandomCorner(){		
		//return null if all corners filled
		if (getCellSymbol(0,0).equals(" ") ||
			getCellSymbol(0,2).equals(" ") ||
			getCellSymbol(2,0).equals(" ") ||
			getCellSymbol(2,2).equals(" ")){
			
			while(true){
				int pos[]=getRandomCorner();
				if (getCellSymbol(pos[0],pos[1]).equals(" ")) return pos;
			}
		}
		return null;
	}

	/**
	 *	Try to apply the win/block strategy. If cant, then play randomly
	 *	@return Array containing the row and column value. 0-row, 1-column
	 */
	private int[] strategyNormal(){
		int pos[] = tryWinOrBlock();		
		if (pos!=null) return pos;
		return strategyRandom();
	}	

	/**
	 *	Counts the number of symbols that has been placed on the cell grid.
	 *	@return	The number of non-vacant cells
	 */
	private int getSymbolCount(){
		int count=0;
		for (int r=0; r<3; r++){
			for (int c=0; c<3; c++){
				if (!getCellSymbol(r,c).equals(" ")) count++;
			}
		}
		return count;
	}
	
	/**
	 *	Return a string containing the pattern of the cell grid, read from
	 *	left to right, top to bottom. Computer symbols are marked with 'C', 
	 *	human with 'H', vacants with 'V'
	 *	@return A string containing all the symbols of the cell (linearly)
	 */
	private String getPattern(){
		String pattern="";
		for (int r=0; r<3; r++){
			for (int c=0; c<3; c++){
				String symbol=getCellSymbol(r,c);
				if (symbol.equals(" "))
					pattern+=".";
				else{
					//we assume current turn is computer's (playerTurn=false)
					if (symbol.equals(getCurrentSymbol())) pattern+="C";
					else pattern+="H";						
				}
			}
		}
		return pattern;
	}
	
	/**
	 *	In addition to having the capbility to take wins and block moves, now it
	 *	has the ability to recognize patterns that could trick the user by having
	 *	2 cells that could cause a victory. Also identify if the human player
	 *	is attempting to use these tricks on the computer as well.
	 *	@return Array containing the row and column value. 0-row, 1-column
	 */
	private int[] strategyPattern(){

		lastPattern = getPattern();
		int defPos[] = tryWinOrBlock();		
		if (defPos!=null) return defPos;

		//get the current turn
		int turn = getSymbolCount();
		switch(turn){
		
		// Legend of the cell grids below:
		// H - Human
		// C - Computer
		// . - Vacant
		// * - Safe cell to place next move
		
		case 0:	//computer starts, board is empty

			//if computer starts first, take any corner
			//  *.*
			//  ...
			//  *.*
			return getRandomCorner();
			
		case 1:	//human started.

			//if human took middle, comp takes corner.
			//if human took anything else, comp takes middle
			//  H..    *..
			//  .*.    .H.
			//  ...    ...
			if (!getCellSymbol(1,1).equals(" "))
				return getRandomCorner();
			else{
				int pos[] = {1,1}; 
				return pos;
			}
		
		case 2:	//computer started, computer's 2nd move

			//human did not take middle on their turn
			//this step is important as it will may generate a win for the comp
			//	CH.   C.H   C.*    C.*
			//	...   ...   ..H    ...
			//	*..   *..   ...    *.H
			if (lastPattern.equals("CH.......")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals("C.H......")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals("C..H.....")) {int pos[]= {0,2}; return pos;}
			if (lastPattern.equals("C....H...")) {int pos[]= {0,2}; return pos;}
			if (lastPattern.equals("C.....H..")) {int pos[]= {0,2}; return pos;}
			if (lastPattern.equals("C......H.")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals("C.......H")) {return getEmptyRandomCorner();}

			if (lastPattern.equals("H.C......")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals(".HC......")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals("..CH.....")) {int pos[]= {0,0}; return pos;}
			if (lastPattern.equals("..C..H...")) {int pos[]= {0,0}; return pos;}
			if (lastPattern.equals("..C...H..")) {return getEmptyRandomCorner();}
			if (lastPattern.equals("..C....H.")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals("..C.....H")) {int pos[]= {0,0}; return pos;}
			
			if (lastPattern.equals("H.....C..")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals(".H....C..")) {int pos[]= {0,0}; return pos;}
			if (lastPattern.equals("..H...C..")) {return getEmptyRandomCorner();}
			if (lastPattern.equals("...H..C..")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals(".....HC..")) {int pos[]= {2,2}; return pos;}			
			if (lastPattern.equals("......CH.")) {int pos[]= {0,0}; return pos;}
			if (lastPattern.equals("......C.H")) {int pos[]= {0,0}; return pos;}

			if (lastPattern.equals("H.......C")) {return getEmptyRandomCorner();}
			if (lastPattern.equals(".H......C")) {int pos[]= {0,2}; return pos;}
			if (lastPattern.equals("..H.....C")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals("...H....C")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals(".....H..C")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals("......H.C")) {int pos[]= {0,2}; return pos;}
			if (lastPattern.equals(".......HC")) {int pos[]= {0,2}; return pos;}

			
			//however if human took the middle
			//  C..    **.    .**    ..C
			//  .H*    *H.    .H*    *H.
			//  .**    ..C    C..    **.
			if (lastPattern.equals("C...H....")) {
				int pos[][]= {{1,2},{2,1},{2,2}};	//3 possible moves
				return (pos[Math.abs(rnd.nextInt())%3]);				
			}
			if (lastPattern.equals("..C.H....")) {
				int pos[][]= {{1,0},{2,0},{2,1}};	//3 possible moves
				return (pos[Math.abs(rnd.nextInt())%3]);				
			}
			if (lastPattern.equals("....H.C..")) {
				int pos[][]= {{0,1},{0,2},{1,2}};	//3 possible moves
				return (pos[Math.abs(rnd.nextInt())%3]);				
			}
			if (lastPattern.equals("....H...C")) {
				int pos[][]= {{0,0},{0,1},{1,0}};	//3 possible moves
				return (pos[Math.abs(rnd.nextInt())%3]);				
			}
			
		case 3: //human started, computer's 2nd move
		
			//if human took middle, computer and human took opposing corners
			//  C.*    *.C    *.H    H.*
			//  .H.    .H.    .H.    .H.
			//  *.H    H.*    C.*    *.C
			if (lastPattern.equals("C...H...H") ||
				lastPattern.equals("H...H...C")) {
				int pos[][]= {{0,2},{2,0}};	//2 possible moves
				return (pos[Math.abs(rnd.nextInt())%2]);
			}
			if (lastPattern.equals("..C.H.H..") ||
				lastPattern.equals("..H.H.C..")) {
				int pos[][]= {{0,0},{2,2}};	//2 possible moves
				return (pos[Math.abs(rnd.nextInt())%2]);
			}
			
			//if computer took middle,
			//and human took 2 opposing corners
			//  H*.    .*H
			//  *C*    *C*
			//  .*H    H*.
			if (lastPattern.equals("H...C...H") ||
				lastPattern.equals("..H.C.H..")) {
				int pos[][]= {{0,1},{1,0},{1,2},{2,1}};	//4 possible moves
				return (pos[Math.abs(rnd.nextInt())%4]);
			}					

			//if computer took middle,
			//and human took both sides
			//  .H*    *H.    ...    ...
			//  .CH    HC.    HC.    .CH
			//  ...    ...    *H.    .H*
			if (lastPattern.equals(".H..CH...")) {int pos[]= {0,2}; return pos;}
			if (lastPattern.equals("....CH.H.")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals(".H.HC....")) {int pos[]= {0,0}; return pos;}
			if (lastPattern.equals("...HC..H.")) {int pos[]= {2,0}; return pos;}
			
			//  *H*    *.*
			//  .C.    HCH
			//  *H*    *.*
			if (lastPattern.equals("...HCH...")||
				lastPattern.equals(".H..C..H.")) {return getRandomCorner();}
			
			//if computer took middle,
			//and human took a corner and a side
			//  H..    ..H    *.H    ...
			//  .C.    .C.    HC.    HC.
			//  *H.    .H*    ...    *.H
			if (lastPattern.equals("H...C..H.")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals("..H.C..H.")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals(".H..C.H..")) {int pos[]= {0,0}; return pos;}
			if (lastPattern.equals(".H..C...H")) {int pos[]= {0,2}; return pos;}			
			if (lastPattern.equals("H...CH...")) {int pos[]= {0,2}; return pos;}
			if (lastPattern.equals("....CHH..")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals("..HHC....")) {int pos[]= {0,0}; return pos;}
			if (lastPattern.equals("...HC...H")) {int pos[]= {2,0}; return pos;}

		case 4: //computer started, computer's 3rd move
		
			//this is the step that will secure victory for the comp
			//if human has not taken middle
			//  CHC    ..*    CH.    C.*
			//  H..    H..    H..    H..
			//  ..*    CHC    C.*    CH.
			if (lastPattern.equals("CHCH.....")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals("CHC..H...")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals("CHC...H..")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals("CHC.....H")) {int pos[]= {2,0}; return pos;}

			if (lastPattern.equals("H.....CHC")) {int pos[]= {0,2}; return pos;}
			if (lastPattern.equals("..H...CHC")) {int pos[]= {0,0}; return pos;}
			if (lastPattern.equals("...H..CHC")) {int pos[]= {0,2}; return pos;}
			if (lastPattern.equals(".....HCHC")) {int pos[]= {0,0}; return pos;}

			if (lastPattern.equals("CH.H..C..")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals("C.HH..C..")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals("C..H..CH.")) {int pos[]= {0,2}; return pos;}
			if (lastPattern.equals("C..H..C.H")) {int pos[]= {0,2}; return pos;}
			
			if (lastPattern.equals("H.C..H..C")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals(".HC..H..C")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals("..C..HH.C")) {int pos[]= {0,0}; return pos;}
			if (lastPattern.equals("..C..H.HC")) {int pos[]= {0,0}; return pos;}

			//if human had taken middle earlier,
			//  CH.    ...
			//  .H.    HHC
			//  *C.    C.*
			if (lastPattern.equals("CH..H..C.")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals(".HC.H..C.")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals(".C..H.CH.")) {int pos[]= {0,0}; return pos;}
			if (lastPattern.equals(".C..H..HC")) {int pos[]= {0,2}; return pos;}
			
		case 6:	//computer started, computer's 4th move
			
			//This move will still secure a victory for the computer. Usually
			//human who played badly will reach this pattern state.
			//  CH.
			//  HHC
			//  .C*
			if (lastPattern.equals("CH.HHC.C.")) {int pos[]= {2,2}; return pos;}
			if (lastPattern.equals(".HCCHH.C.")) {int pos[]= {2,0}; return pos;}
			if (lastPattern.equals(".C.CHH.HC")) {int pos[]= {0,0}; return pos;}
			if (lastPattern.equals(".C.HHCCH.")) {int pos[]= {0,2}; return pos;}
		}
		
		//use random move for all other occasions
		return strategyRandom();
	}	
}
