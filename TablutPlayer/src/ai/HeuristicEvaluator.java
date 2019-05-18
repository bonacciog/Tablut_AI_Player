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
		if(i>0 && i<8 && j>0 && j<8) {
			if (state.getBoard()[i][j+1].equalsPawn(pawn)|| isCamp(i,j) || isThrone(i,j))
				return true;
		}
		return false;
	}
	
	protected boolean LeftNear(State state,int i,int j, String pawn) {
		if(i>0 && i<8 && j>0 && j<8) {
			if (state.getBoard()[i][j-1].equalsPawn(pawn)|| isCamp(i,j) || isThrone(i,j))
				return true;
		}
		return false;
	}
	
	protected boolean UpNear(State state,int i,int j, String pawn) {
		if(i>0 && i<8 && j>0 && j<8) {
			if (state.getBoard()[i+1][j].equalsPawn(pawn)|| isCamp(i,j) || isThrone(i,j))
				return true;
		}
		return false;
	}
	
	protected boolean DownNear(State state,int i,int j, String pawn) {
		if(i>0 && i<8 && j>0 && j<8) {
			if (state.getBoard()[i-1][j].equalsPawn(pawn)|| isCamp(i,j) || isThrone(i,j))
				return true;
		}
		return false;
	}
	
	protected boolean isCamp(int i, int j){
		if( (i==0 && (j>=3 && j<=5)) || (i==1 && j==4) || 
		    (i==8 && (j>=3 && j<=5)) || (i==7 && j==4) ||
		    (j==0 && (i>=3 && i<=5)) || (j==1 && i==4) ||
		    (j==8 && (i>=3 && i<=5)) || (j==7 && i==4)  )
			return true;
		else
			return false;
	}
	
	protected boolean isThrone(int i,int j) {
		if (i==4 && j==4)
			return true;
		else
			return false;

	}
	
	
}
