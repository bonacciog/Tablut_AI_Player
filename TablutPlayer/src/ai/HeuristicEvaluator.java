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
	protected int getKingDistanceValue(int i,int j,State arg0) {
		if(!(arg0 instanceof StateTablut))
			throw new IllegalArgumentException();
		StateTablut state = (StateTablut) arg0;
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
		if( state.emptyLine(i,j,"row") && (i==2 || i==6) || state.emptyLine(i,j,"column") && (j==2 || j==6) )
			kingDistanceValue=800; 
		// il king ha raggiungo il bordo e ha vinto .
		if(i==0 || i==8 || j==0 || j==8)
			kingDistanceValue= 1000;

		
		return kingDistanceValue;
	}
	
	
}
