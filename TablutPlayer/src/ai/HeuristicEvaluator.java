package ai;

import domain.State;

public abstract class HeuristicEvaluator {
	public static final int TOTWHITE=8;
	public static final int TOTBLACK=12;
	public static final int STATEINITIALVALUE=1000;
	
	public abstract int getEvaluation(State state);
	public int getKingDistanceValue(int i,int j,State state) {
		
		int kingDistanceValue=0;
		// 1° il King è nel trono.(valore del re vivo, alto).
		if(i==4 && j==4)
			kingDistanceValue= 500; 
		//2° il king è a distanza 1 dal trono.
		if( (i==3 && j>=3 && j<=5) || (i==5 && j>=3 && j<=5) || (j==3 && i>= 3 && i<=5) || (j==5 && i>=3 && i<=5))
			kingDistanceValue= 520;	
		//3° il king è a distanza 2 dal trono.
		if( (i==2 && j>=2 && j<=6) || (i==6 && j>=2 && j<=6) || (j==2 && i>= 2 && i<=6) || (j==6 && i>=2 && i<=6))			
			kingDistanceValue= 540;
		//4° il king è a distanza 3 dal trono.
		if( (i==1 && j>=1 && j<=7) || (i==7 && j>=1 && j<=7) || (j==1 && i>= 1 && i<=7) || (j==7 && i>=1 && i<=7)) 			
			kingDistanceValue= 560;
		//il king è prossimo alla vittoria.
		if( emptyLine(i,j,"row",state) && (i==2 || i==6) || emptyLine(i,j,"column",state) && (j==2 || j==6) )
			kingDistanceValue=800; 
		// il king ha raggiungo il bordo e ha vinto .
		if(i==0 || i==8 || j==0 || j==8)
			kingDistanceValue= 1000;

		
		return kingDistanceValue;
	}
	
	public boolean emptyLine(int i,int j,String line, State state) {
		boolean empty = true;
		if(line.equalsIgnoreCase("row")) {//controllo la riga
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
}
