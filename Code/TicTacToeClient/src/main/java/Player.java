import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	boolean isAI;
	String difficulty;
	int turn;
	String piece;
	int points;
	String username;
	String ProfilePic;
	int LeaderBoard;

	public Player(int turnNumber){
		turn = turnNumber;
		isAI = false;
		points = 0;
		username = "Player";
		ProfilePic = "GenericProfilePic.png";
		LeaderBoard = -1;
		piece = "";
	}

	public void setTurn(int val) {
		turn = val;
	}
	public int isTurn() {
		return turn;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}
	public String getPiece() {
		return piece;
	}

	public void incrementPoints() {
		points++;
	}
	public int getPoints() {
		return points;
	}

	public void setUsername(String user) {
		username = user;
	}

	public void setPic(String pic) {
		ProfilePic = pic;
	}

	public void setLeaderBoard(int x) {
		LeaderBoard = x;
	}
	public int getLeaderBoard() {
		return LeaderBoard;
	}

	public String getDifficulty() { return difficulty; }
	public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}