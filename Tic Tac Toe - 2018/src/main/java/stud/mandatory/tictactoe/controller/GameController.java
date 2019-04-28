package stud.mandatory.tictactoe.controller;

import stud.mandatory.tictactoe.game.Game;
import stud.mandatory.tictactoe.game.board.Tag;
import stud.mandatory.tictactoe.game.players.Player;
import stud.mandatory.tictactoe.model.Account;
import stud.mandatory.tictactoe.model.Square;

public class GameController {
	//constants for the Tags available in the game
	public static final Tag BLANK = new Tag( '#');
	private static final Tag PLAYER_ONE = new Tag('X');
	private static final Tag PLAYER_TWO = new Tag('O');

	private Player compPlayer = new Player("Computer", PLAYER_TWO);

	private Game game;

	Square[] getBoard(){
		return game.getBoard();
	}

	//starts a new game
	void newGame(Account userAccount) {
		game = new Game(new Player(userAccount, PLAYER_ONE), compPlayer);
	}

	//attempts to place a tag on the board
    int place(int id) {
		return game.makeTurn(id);
    }
}