package stud.mandatory.tictactoe.game;

import stud.mandatory.tictactoe.game.board.Board;
import stud.mandatory.tictactoe.game.players.Player;
import stud.mandatory.tictactoe.model.Square;

import java.util.ArrayList;
import java.util.Random;

public class Game {

	private Player[] players;
	private Player currentPlayer;
	private int turns;
	private Board board;

	//list of all 8 possible winning patterns
	private static final String[] WIN_PATTERNS = new String[]{
		"036", "147", "258", //vertical lines
		"012", "345", "678", //horizontal lines
		"048", "246"	//diagonal lines
	};

	public Game(Player player1, Player player2) {
		this.players = new Player[]{player1, player2};
		this.turns = -1; // gets set to 0 after the first switchPlayer() call
		switchPlayer();
		board = new Board();
	}

	//called in the constructor to set the current player
	//and after each turn ends
	void switchPlayer() {
		turns++;
		//after each move, the turns variable is incremented by 1,
		//and the current player is determined by taking the remainder
		//of turns modulo 2 and passing the result as an index for the players[] array
		currentPlayer = players[turns % 2];
	}

	//makes a human controlled turn
	public int makeTurn(int pos){
		int result = -2; //default value, meaning the game is still going on
		if (board.placeTag(currentPlayer, pos)){
			//the player chose a valid square to place their tag
			//so their turn ends
			switchPlayer();
			//checks the current state of the game
			result = getState();
			if (result == -2)
				result = makeAITurn();
		}
		return result;
	}

	//makes a computer controlled turn,
	private int makeAITurn(){
		Random rand = new Random();
		//gets a random integer from 0 to 8 until an empty square with that number as id is found,
		//then place the tag there
		while(!board.placeTag(currentPlayer, rand.nextInt(9)));
		switchPlayer();
		return getState();
	}

	/*
	return values:
	-2 -- no wins, the game keeps going on
	-1 -- tie
	 0 -- player 1 wins
	 1 -- player 2 wins
	 */
	private int getState(){
		Square[] squares = board.getSquares();

		//checks if a player won and returns their index
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];

			//a list to store the positions where the player placed tags
			ArrayList<Integer> positions = new ArrayList<>();
			//loops through all the squares, and for each one
			//whose tag matches the player's, add its index to the positions list
			for (int j = 0; j < squares.length; j++)
				if (squares[j].getTag().equals(player.getTag()))
					positions.add(j);

			//a "combo" variable, which defines a winner when its value reaches "3"
			int x = 0;
			//loops through all the winnable patterns
			for (String pattern : WIN_PATTERNS) {

				//loops through each position defined in the current pattern
				for (int j = 0; j < 3; j++) {
					//if the player's list of positions contains the position defined in the pattern,
					//increment the "x" variable
					//if not, reset the variable and break the loop,
					//in order to verify the next winnable pattern
					if (positions.contains(Integer.parseInt(""+pattern.charAt(j)))) {
						x++;
					} else {
						x = 0;
						break;
					}
				}
				//if the value of x is 3, the player won, so return its index in the players array
				if (x == 3)
					return i;
			}
		}

		//if there is no winner and the turns count reached 9 (all squares are full),
		//return -1 to end the game in a tie
		//if there are any turns left, return -2 to continue the game
		return turns == 9 ? -1 : -2;
	}

	public Square[] getBoard(){
		return board.getSquares();
	}
}