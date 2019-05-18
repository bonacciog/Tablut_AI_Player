package ai;

import domain.State;
/**
 * This class implements a function which return a black heuristic evaluation
 * 
 * @author Paolo Caligiana, Alessandro Fossa
 *
 */
public class BlackHeuristicEvaluator extends HeuristicEvaluator{

	
	public int getEvaluation(State state) {
		//considerazioni generali sulla BlackHeuristic:
		// è più importante mangiare pedine rispetto alla vicinanza del re al bordo.
		
		int whiteCaptured=HeuristicEvaluator.TOTWHITE;
		int blackCaptured=HeuristicEvaluator.TOTBLACK;
		int stateValue=HeuristicEvaluator.STATEINITIALVALUE;
		boolean kingCaptured=true;
		
		for(int i=0; i<9;i++) {
			for(int j=0; j<9; j++) {

				if(state.getPawn(i, j).equalsPawn("K")) { 
					kingCaptured=false;
					switch(getKingDistance(i,j,state)){
					case 0: 
						if(this.countNear(state, i, j, "B")==4)
							stateValue= stateValue +1000;
						break;
					case 1: 
						if(this.countNear(state, i, j, "B")==4)
							stateValue= stateValue +1000;
						else
							stateValue= stateValue -20; //King a distanza 1
						break;
					case 2: 
						if( (this.RightNear(state, i, j, "B") && this.LeftNear(state, i, j, "B"))  || (this.UpNear(state, i, j, "B") && this.DownNear(state, i, j, "B")) )
							stateValue= stateValue +1000;
						else
							stateValue= stateValue -40;
						break;
					case 3: 
						if( (this.RightNear(state, i, j, "B") && this.LeftNear(state, i, j, "B"))  || (this.UpNear(state, i, j, "B") && this.DownNear(state, i, j, "B")) )
							stateValue= stateValue +1000;
						else
							stateValue= stateValue -80;
						break;
					case 4: stateValue= 0; //King ha vinto.
					}
					stateValue= stateValue + 100 * (countNear(state,i,j,"B"));
				}
				if(state.getPawn(i, j).equalsPawn("W"))
					whiteCaptured--;
				if(state.getPawn(i, j).equalsPawn("B")) {
					blackCaptured--;
					
				}
					
				//aumento il valore per le pedine nere sulle diagonali
				if( (i==1 && (j==3 || j==5) ) ||  (i==3 && (j==1|| j==7))|| (i==5 && (j==1|| j==7))|| (i==7 && (j==3|| j==5)) ){			
					if(state.getPawn(i, j).equalsPawn("B"))
						stateValue= stateValue + 20;
				}
				//aumento il valore per le pedine nere sulle caselle più importanti.
				if( (i==2 && (j==2 || j==6) ) ||  (i==6 && (j==2|| j==6))){			
					if(state.getPawn(i, j).equalsPawn("B"))
						stateValue= stateValue + 45;
				}
			}
		}
		stateValue= stateValue + 70*(whiteCaptured -  blackCaptured);
		if (kingCaptured)
			stateValue= stateValue +1000;
		
		return stateValue;
	}
	

}
