package ai;

import domain.State;
import domain.StateTablut;
/**
 * This class implements a function which return a black heuristic evaluation
 * 
 * @author Paolo Caligiana
 *
 */
public abstract class HeuristicEvaluator {
	protected static final int TOTWHITE=8;
	protected static final int TOTBLACK=12;
	protected static final int STATEINITIALVALUE=1000;
	
	public abstract int getEvaluation(State state);
	

	protected int getKingDistance(int i,int j,State arg0) {
		if(!(arg0 instanceof StateTablut))
			throw new IllegalArgumentException();
		int kingDistance=0;
		// 0° il King è nel trono.(valore del re vivo, alto).
		if(i==4 && j==4)
			kingDistance= 0; 
		//1° il king è a distanza 1 dal trono.
		if( (i==3 || i==5) && j==4 || (i==4 && (j==3 || j==5)))
			kingDistance= 1;	
		//2° il king è a distanza 2 dal trono.
		if((i==5 && (j==3 || j==5)) || (i==3 && (j==3 || j==5)) || (i==2 && j>=3 && j<=5) || (i==6 && j>=3 && j<=5) || (j==2 && i>= 3 && i<=5) || (j==6 && i>=3 && i<=5))
			kingDistance= 2;
		//3° il king è a distanza 3 dal trono.
		if((i==6 && (j==2 || j==6)) ||(i==2 && (j==2 || j==6)) || (i==1 && j>=1 && j<=7) || (i==7 && j>=1 && j<=7) || (j==1 && i>= 1 && i<=7) || (j==7 && i>=1 && i<=7)) 			
			kingDistance= 3;
		//4° il king ha raggiungo il bordo e ha vinto .
		if(i==0 || i==8 || j==0 || j==8)
			kingDistance= 4;

		return kingDistance;
	}
	
	protected int countNear(State state,int i,int j, String pawn) {
		int totNear=0;
		if(i>0 && i<8 && j>0 && j<8) {
			if (RightNear(state, i, j, pawn))
				totNear++;
			if (LeftNear(state, i, j, pawn))
				totNear++;
			if (UpNear(state, i, j, pawn))
				totNear++;
			if (DownNear(state, i, j, pawn))
				totNear++;
		}
		return totNear;
	}
	
	
	
	protected boolean RightNear(State state,int i,int j, String pawn) {
		return (i>0 && i<8 && j>0 && j<8) &&
			(state.getBoard()[i][j+1].equalsPawn(pawn)|| isCamp(i,j) || isThrone(i,j));
	}
	
	protected boolean LeftNear(State state,int i,int j, String pawn) {
		return (i>0 && i<8 && j>0 && j<8) &&
				(state.getBoard()[i][j-1].equalsPawn(pawn)|| isCamp(i,j) || isThrone(i,j));
	}
	
	protected boolean UpNear(State state,int i,int j, String pawn) {
		return(i>0 && i<8 && j>0 && j<8) &&
				(state.getBoard()[i+1][j].equalsPawn(pawn)|| isCamp(i,j) || isThrone(i,j));
	}
	
	protected boolean DownNear(State state,int i,int j, String pawn) {
		return((i>0 && i<8 && j>0 && j<8) &&
			 (state.getBoard()[i-1][j].equalsPawn(pawn)|| isCamp(i,j) || isThrone(i,j)));
	}
	
	protected boolean isCamp(int i, int j){
		return( (i==0 && (j>=3 && j<=5)) || (i==1 && j==4) || 
		    (i==8 && (j>=3 && j<=5)) || (i==7 && j==4) ||
		    (j==0 && (i>=3 && i<=5)) || (j==1 && i==4) ||
		    (j==8 && (i>=3 && i<=5)) || (j==7 && i==4)  );
	}
	
	protected boolean isThrone(int i,int j) {
		return (i==4 && j==4);
	}
	
	protected boolean emptyLine(int i,int j,String line,State state) {
		boolean empty = true;
		if(line.equalsIgnoreCase("row")) {
			for(int k=0; k<9; k++) {
				if(k!=j) {
					if(state.getPawn(i,k).equalsPawn("W") || state.getPawn(i,k).equalsPawn("B"))
						empty=false;
				}
			}
		}
		else if(line.equalsIgnoreCase("column")) {//controllo la colonna
			for(int k=0; k<9; k++) {
				if(k!=i) {
					if(state.getPawn(k,j).equalsPawn("W") || state.getPawn(k,j).equalsPawn("B"))
						empty=false;
				}
			}
		}
		else {
			System.err.println("expected 'row' or 'column' in the third field");
		}
		return empty;
	}
	
	protected boolean isInLine(int i,int j,String line,String pawn, State state) {
		boolean result = false;
		if(line.equalsIgnoreCase("row")) {
			for(int k=0; k<9; k++) {
				if(k!=j) {
					if(state.getPawn(i,k).equalsPawn(pawn))
						result=true;
				}
			}
		}
		else if(line.equalsIgnoreCase("column")) {//controllo la colonna
			for(int k=0; k<9; k++) {
				if(k!=i) {
					if(state.getPawn(k,j).equalsPawn(pawn))
						result=true;
				}
			}
		}
		else {
			System.err.println("expected 'row' or 'column' in the third field");
		}
		return result;
	}
	
	protected boolean SecureWhiteLine(int i, int j,String line, State state) { //è prettamente per l'euristica Bianca
		if(this.emptyLine(i, j, line, state) || !this.isInLine(i, j, line, "B", state))
			return true;
		else {

			if(line.equals("column")) {
				for(int k=i+1; k<9;k++) {
					if(state.getBoard()[k][j].equalsPawn("W")){
						k=8;
					}
					if(state.getBoard()[k][j].equalsPawn("B")){
						return false;
					}	
				}
				for(int k=i-1; k>=0;k--) {
					if(state.getBoard()[k][j].equalsPawn("W")){
						k=0;
					}
					if(state.getBoard()[k][j].equalsPawn("B")){
						return false;
					}	
				}
				
			}
			else {
				for(int k=j+1; k<9;k++) {
					if(state.getBoard()[k][j].equalsPawn("W")){
						k=8;
					}
					if(state.getBoard()[k][j].equalsPawn("B")){
						return false;
					}	
				}
				for(int k=j-1; k>=0;k--) {
					if(state.getBoard()[k][j].equalsPawn("W")){
						k=0;
					}
					if(state.getBoard()[k][j].equalsPawn("B")){
						return false;
					}	
				}
			}
			return true;
		}
	}
}
