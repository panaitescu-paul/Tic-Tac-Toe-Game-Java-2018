package stud.mandatory.tictactoe.game.board;

import stud.mandatory.tictactoe.controller.GameController;
import stud.mandatory.tictactoe.game.players.Player;
import stud.mandatory.tictactoe.model.Square;

//the main game board, containing 9 squares
public class Board {

	private Square[] squares = new Square[9];

	public Board(){
		//fills the board with empty tags
		for (int i = 0; i < squares.length; i++)
			squares[i] = new Square(i, GameController.BLANK);
	}

	//using the tag provided by the player, and the position of the square,
	//check if the square is blank, and place the player tag inside
	public boolean placeTag(Player player, int pos) {
		if (squares[pos].getTag().equals(GameController.BLANK)){
			squares[pos].setTag(player.getTag());
			return true;
		}
		else
			return false;
	}

	//return the array of the squares in the board
	public Square[] getSquares(){
		return squares;
	}
}