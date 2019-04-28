package stud.mandatory.tictactoe.model;

import stud.mandatory.tictactoe.game.board.Tag;

// one of the 9 boxes of the board that contains the tag of a player
public class Square {
    private int id;
    private Tag tag;

    public Square(int id, Tag tag) {
        this.id = id;
        this.tag = tag;
    }

    public Tag getTag() {return tag;}
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}