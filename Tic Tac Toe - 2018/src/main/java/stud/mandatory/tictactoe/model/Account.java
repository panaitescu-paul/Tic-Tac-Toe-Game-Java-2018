package stud.mandatory.tictactoe.model;

public class Account implements Comparable<Account>{

	private int id;
	private String nickname;
	private String password;
	private int wins;
	private int losses;
	private int ties;

	public Account(int id, String nickname, String password, int wins, int losses, int ties) {
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.wins = wins;
		this.losses = losses;
		this.ties = ties;
	}

	public Account(){

	}

	//return a double from 0 to 1 representing the
	//winning percentage of the player
	private double getWinPercentage(){
		//if the player had no previous matches played, return 0
		if (wins+losses+ties==0)
			return 0;

		return (wins + 0.5 * ties)/(wins + losses + ties);
	}

	public void incrementWins(){
		wins++;
	}
	public void incrementLosses(){
		losses++;
	}
	public void incrementTies(){
		ties++;
	}

	@Override
	public int compareTo(Account that) {
		//compared to regular Comparable interfaces, this method returns an inverted
		//result, to reverse the sorting order from ascending to descending
		return Double.compare(that.getWinPercentage(), this.getWinPercentage());
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getTies() {
		return ties;
	}
	public void setTies(int ties) {
		this.ties = ties;
	}
}