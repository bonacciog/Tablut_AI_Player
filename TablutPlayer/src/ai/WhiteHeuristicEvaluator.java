package ai;

import java.util.Random;

import domain.State;

public class WhiteHeuristicEvaluator implements HeuristicEvaluator{

	@Override
	public int getEvaluation(State state) {
		// TODO Auto-generated method stub
		return new Random().nextInt(100);
	}

}
