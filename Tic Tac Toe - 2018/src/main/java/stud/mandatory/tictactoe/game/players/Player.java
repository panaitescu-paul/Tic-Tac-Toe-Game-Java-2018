package stud.mandatory.tictactoe.game.players;

import stud.mandatory.tictactoe.game.board.Tag;
import stud.mandatory.tictactoe.model.Account;

//basic player class
public class Player {

	private String nick;
	protected Tag tag;

	public Player(String nick, Tag tag){
		this.nick = nick;
		this.tag = tag;
	}

	public Player(Account userAccount, Tag tag){
		this(userAccount.getNickname(), tag);
	}

	public String getNick() {
		return nick;
	}

	public Tag getTag() {
		return tag;
	}
}