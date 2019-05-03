package ai;

import domain.State;

public interface HeuristicEvaluator {
	public int getEvaluation(State state);
}
