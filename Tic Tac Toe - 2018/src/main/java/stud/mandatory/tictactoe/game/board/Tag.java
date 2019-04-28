package stud.mandatory.tictactoe.game.board;

//used to show the player ownership of a square
public class Tag {

	private char type;

	public Tag(char type) {
		this.type = type;
	}

	public boolean equals(Tag that){
		return this.type == that.type;
	}

	public char getType() {
		return type;
	}
}