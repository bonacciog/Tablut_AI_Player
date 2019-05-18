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
						break;
					case 1: //King a distanza 1.
						if(countNear(state, i, j, "B")<=2)
							stateValue= stateValue + 520;
						break;
					case 2:
						if(countNear(state, i, j, "B")<=1)
							if( (RightNear(state, i, j, "B") && stateT.SecureLine(i, j-1, "column")) ||
								(LeftNear(state, i, j, "B") && stateT.SecureLine(i, j+1, "column"))  ||
								(UpNear(state, i, j, "B") && stateT.SecureLine(i-1, j, "row")) ||
								(DownNear(state, i, j, "B") && stateT.SecureLine(i+1, j, "row")) )									
								stateValue= stateValue - 100; //alla prossima perdo (anche senza decrementare andava bene)
							else
								stateValue= stateValue +540;
						break;
					case 3: 
						if(countNear(state, i, j, "B")<=1)
							if( (RightNear(state, i, j, "B") && stateT.isInLine(i, j-1, "column", "B")) ||
								(LeftNear(state, i, j, "B") && stateT.isInLine(i, j+1, "column", "B")) ||
								(UpNear(state, i, j, "B") && stateT.isInLine(i-1, j, "column", "B")) ||
								(DownNear(state, i, j, "B") && stateT.isInLine(i+1, j, "column", "B")) )									
								stateValue= stateValue - 10000; //alla prossima perdo (anche senza decrementare andava bene)
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
		
		//il king è prossimo alla vittoria. (SUPERFLUO	)
		/*if( state.emptyLine(i,j,"row") && (i==2 || i==6) || state.emptyLine(i,j,"column") && (j==2 || j==6) )
			kingDistance=800; 
		*/
		
		
/*	
		
		//NB: QUESTI NON MI CONVINCONO TROPPO-> TESTARE IL GIOCO E NEL CASO RIVEDERLI
	//1° quadrante: pedina bianca sulla diagonale che blocca una delle due nere.
		
		if(state.getPawn(1, 3).equalsPawn("W") && (state.getPawn(0, 3).equalsPawn("B") || state.getPawn(1, 4).equalsPawn("B"))){
			stateValue= stateValue + 20;//posizione favorevole -> +1.
			if(state.getPawn(4, 2).equalsPawn("O"))
				stateValue= stateValue + 5;//posizione ancora più favorevole -> +1
		}
		if(state.getPawn(3, 1).equalsPawn("W") && (state.getPawn(3, 0).equalsPawn("B") || state.getPawn(4, 1).equalsPawn("B"))){
			stateValue= stateValue + 20;//posizione favorevole -> +1.
			if(state.getPawn(2, 4).equalsPawn("O"))
				stateValue= stateValue + 5;//posizione ancora più favorevole -> +1
		}
		
	//2° quadrante: pedina bianca sulla diagonale che blocca una delle due nere.
		
		if(state.getPawn(1, 5).equalsPawn("W") && (state.getPawn(0, 5).equalsPawn("B") || state.getPawn(1, 4).equalsPawn("B"))){
			stateValue= stateValue + 20;//posizione favorevole -> +1.
			if(state.getPawn(4, 6).equalsPawn("O"))
				stateValue= stateValue + 5;//posizione ancora più favorevole -> +1
		}
		if(state.getPawn(3, 7).equalsPawn("W") && (state.getPawn(3, 8).equalsPawn("B") || state.getPawn(4, 7).equalsPawn("B"))){
			stateValue= stateValue + 20;//posizione favorevole -> +1.
			if(state.getPawn(2, 4).equalsPawn("O"))
				stateValue= stateValue + 5;//posizione ancora più favorevole -> +1
		}
	//3° quadrante: pedina bianca sulla diagonale che blocca una delle due nere.
		
		if(state.getPawn(5, 1).equalsPawn("W") && (state.getPawn(4, 1).equalsPawn("B") || state.getPawn(5, 0).equalsPawn("B"))){
			stateValue= stateValue + 20;//posizione favorevole -> +1.
			if(state.getPawn(6, 4).equalsPawn("O"))
				stateValue= stateValue + 5;//posizione ancora più favorevole -> +1
		}
		if(state.getPawn(7, 3).equalsPawn("W") && (state.getPawn(7, 4).equalsPawn("B") || state.getPawn(8, 3).equalsPawn("B"))){
			stateValue= stateValue + 20;//posizione favorevole -> +1.
			if(state.getPawn(4, 2).equalsPawn("O"))
				stateValue= stateValue + 5;//posizione ancora più favorevole -> +1
		}
	//4° quadrante: pedina bianca sulla diagonale che blocca una delle due nere.
		
		if(state.getPawn(7, 5).equalsPawn("W") && (state.getPawn(7, 4).equalsPawn("B") || state.getPawn(8, 5).equalsPawn("B"))){
			stateValue= stateValue + 20;//posizione favorevole -> +1.
			if(state.getPawn(4, 6).equalsPawn("O"))
				stateValue= stateValue + 5;//posizione ancora più favorevole -> +1
		}
		if(state.getPawn(5, 7).equalsPawn("W") && (state.getPawn(4, 7).equalsPawn("B") || state.getPawn(5, 8).equalsPawn("B"))){
			stateValue= stateValue + 20;//posizione favorevole -> +1.
			if(state.getPawn(6, 4).equalsPawn("O"))
				stateValue= stateValue + 5;//posizione ancora più favorevole -> +1
		}
		*/
	
		return stateValue;
	}

	

}
