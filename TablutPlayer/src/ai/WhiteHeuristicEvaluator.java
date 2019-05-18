package ai;

import domain.State;
import domain.StateTablut;

/**
 * This class implements a function which return a white heuristic evaluation
 * 
 * @author Paolo Caligiana
 *
 */

public class WhiteHeuristicEvaluator extends HeuristicEvaluator{
	//considerazioni generali sulla WhiteHeuristic:
	// è più importante verificare e valutare la vicinanza del re al bordo 
	// rispetto al numero di pedine che circondano il re.

	public int getEvaluation(State state) {
		
		int whiteCaptured=HeuristicEvaluator.TOTWHITE;
		int blackCaptured=HeuristicEvaluator.TOTBLACK;
		int stateValue=HeuristicEvaluator.STATEINITIALVALUE;
		StateTablut stateT= (StateTablut)state;

		for(int i=0; i<9;i++) {
			for(int j=0; j<9; j++) {

				if(state.getPawn(i, j).equalsPawn("K")) { 
					switch(getKingDistance(i,j,state)){
					case 0: //King  nel trono
						if(countNear(state, i, j, "B")<=3)
							stateValue= stateValue + 500;
						else
							stateValue= stateValue - 100;
						break;
					case 1: //King a distanza 1.
						if(countNear(state, i, j, "B")<=2)
							stateValue= stateValue + 520;
						else
							stateValue= stateValue - 100;
						break;
					case 2:
						if(countNear(state, i, j, "B")<=1)
							if( (RightNear(state, i, j, "B") && !stateT.SecureWhiteLine(i, j-1, "column")) ||
								(LeftNear(state, i, j, "B") && !stateT.SecureWhiteLine(i, j+1, "column"))  ||
								(UpNear(state, i, j, "B") && !stateT.SecureWhiteLine(i-1, j, "row")) ||
								(DownNear(state, i, j, "B") && !stateT.SecureWhiteLine(i+1, j, "row")) )									
								stateValue= stateValue - 100; //alla prossima perdo (anche senza decrementare andava bene)
							else
								stateValue= stateValue +540;
						break;
					case 3: 
						if(countNear(state, i, j, "B")<=1)
							if( (RightNear(state, i, j, "B") && !stateT.SecureWhiteLine(i, j-1, "column")) ||
									(LeftNear(state, i, j, "B") && !stateT.SecureWhiteLine(i, j+1, "column"))  ||
									(UpNear(state, i, j, "B") && !stateT.SecureWhiteLine(i-1, j, "row")) ||
									(DownNear(state, i, j, "B") && !stateT.SecureWhiteLine(i+1, j, "row")) )									
								stateValue= stateValue - 100; //alla prossima perdo (anche senza decrementare andava bene)
							else
								stateValue= stateValue + 560;
						break;
					case 4: 
						stateValue= stateValue + 1000; //ha vinto.
						break;
					default:
						break;
					}
				}
				if(state.getPawn(i, j).equalsPawn("W"))
					whiteCaptured--;
				if(state.getPawn(i, j).equalsPawn("B"))
					blackCaptured--;
				//aumento il valore per le pedine bianche attorno al trono
				if( (i==4 && j==3) || (i==3 && j==4) || (i==4 && j==5) || (i==5 && j==4)){			
						stateValue= stateValue + 5*(this.countNear(state, i, j, "W"));
				}
			}
		}
		
		stateValue= stateValue + 10*(blackCaptured - whiteCaptured); 
		return stateValue;
	}

	

}
