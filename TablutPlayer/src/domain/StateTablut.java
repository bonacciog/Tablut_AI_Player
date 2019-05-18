package domain;


import java.io.Serializable;



/**
 * This class represents a state of a match of Tablut (classical or second
 * version)
 * 
 * @author A.Piretti
 * 
 */
public class StateTablut extends State implements Serializable {

	private static final long serialVersionUID = 1L;

	public StateTablut() {
		super();
		this.board = new Pawn[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.board[i][j] = Pawn.EMPTY;
			}
		}

		this.board[4][4] = Pawn.THRONE;

		this.turn = Turn.BLACK;

		this.board[4][4] = Pawn.KING;

		this.board[2][4] = Pawn.WHITE;
		this.board[3][4] = Pawn.WHITE;
		this.board[5][4] = Pawn.WHITE;
		this.board[6][4] = Pawn.WHITE;
		this.board[4][2] = Pawn.WHITE;
		this.board[4][3] = Pawn.WHITE;
		this.board[4][5] = Pawn.WHITE;
		this.board[4][6] = Pawn.WHITE;

		this.board[0][3] = Pawn.BLACK;
		this.board[0][4] = Pawn.BLACK;
		this.board[0][5] = Pawn.BLACK;
		this.board[1][4] = Pawn.BLACK;
		this.board[8][3] = Pawn.BLACK;
		this.board[8][4] = Pawn.BLACK;
		this.board[8][5] = Pawn.BLACK;
		this.board[7][4] = Pawn.BLACK;
		this.board[3][0] = Pawn.BLACK;
		this.board[4][0] = Pawn.BLACK;
		this.board[5][0] = Pawn.BLACK;
		this.board[4][1] = Pawn.BLACK;
		this.board[3][8] = Pawn.BLACK;
		this.board[4][8] = Pawn.BLACK;
		this.board[5][8] = Pawn.BLACK;
		this.board[4][7] = Pawn.BLACK;

	}
	
	public StateTablut clone() {
		StateTablut result = new StateTablut();

		Pawn oldboard[][] = this.getBoard();
		Pawn newboard[][] = result.getBoard();

		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				newboard[i][j] = oldboard[i][j];
			}
		}

		result.setBoard(newboard);
		result.setTurn(this.turn);
		return result;
	}
	
	public boolean emptyLine(int i,int j,String line) {
		boolean empty = true;
		if(line.equalsIgnoreCase("row")) {
			for(int k=0; k<9; k++) {
				if(k!=j) {
					if(this.getPawn(i,k).equalsPawn("W") || this.getPawn(i,k).equalsPawn("B"))
						empty=false;
				}
			}
		}
		else if(line.equalsIgnoreCase("column")) {//controllo la colonna
			for(int k=0; k<9; k++) {
				if(k!=i) {
					if(this.getPawn(k,j).equalsPawn("W") || this.getPawn(k,j).equalsPawn("B"))
						empty=false;
				}
			}
		}
		else {
			System.err.println("expected 'row' or 'column' in the third field");
		}
		return empty;
	}
	
	public boolean isInLine(int i,int j,String line,String pawn) {
		boolean empty = true;
		if(line.equalsIgnoreCase("row")) {
			for(int k=0; k<9; k++) {
				if(k!=j) {
					if(this.getPawn(i,k).equalsPawn(pawn))
						empty=false;
				}
			}
		}
		else if(line.equalsIgnoreCase("column")) {//controllo la colonna
			for(int k=0; k<9; k++) {
				if(k!=i) {
					if(this.getPawn(k,j).equalsPawn(pawn))
						empty=false;
				}
			}
		}
		else {
			System.err.println("expected 'row' or 'column' in the third field");
		}
		return empty;
	}
	
	public boolean SecureLine(int i, int j,String line) {
		if(this.emptyLine(i, j, line) || !this.isInLine(i, j, line, "B"))
			return true;
		else {

			if(line.equals("column")) {
				for(int k=i+1; k<9;k++) {
					if(getBoard()[k][j].equalsPawn("W")){
						k=8;
					}
					if(getBoard()[k][j].equalsPawn("B")){
						return false;
					}	
				}
				for(int k=i-1; k>=0;k--) {
					if(getBoard()[k][j].equalsPawn("W")){
						k=0;
					}
					if(getBoard()[k][j].equalsPawn("B")){
						return false;
					}	
				}
				
			}
			else {
				for(int k=j+1; k<9;k++) {
					if(getBoard()[k][j].equalsPawn("W")){
						k=8;
					}
					if(getBoard()[k][j].equalsPawn("B")){
						return false;
					}	
				}
				for(int k=j-1; k>=0;k--) {
					if(getBoard()[k][j].equalsPawn("W")){
						k=0;
					}
					if(getBoard()[k][j].equalsPawn("B")){
						return false;
					}	
				}
			}
			return true;
		}
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		StateTablut other = (StateTablut) obj;
		if (this.board == null) {
			if (other.board != null)
				return false;
		} else {
			if (other.board == null)
				return false;
			if (this.board.length != other.board.length)
				return false;
			if (this.board[0].length != other.board[0].length)
				return false;
			for (int i = 0; i < other.board.length; i++)
				for (int j = 0; j < other.board[i].length; j++)
					if (!this.board[i][j].equals(other.board[i][j]))
						return false;
		}
		if (this.turn != other.turn)
			return false;
		return true;
	}
}
