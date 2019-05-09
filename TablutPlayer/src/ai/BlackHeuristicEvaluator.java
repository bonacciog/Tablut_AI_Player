package ai;

import java.util.Random;

import domain.State;

public class BlackHeuristicEvaluator implements HeuristicEvaluator{

	@Override
	public int getEvaluation(State state) {
		return new Random().nextInt(100);
	}

}
