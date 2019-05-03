package ai;

import java.util.HashMap;
import java.util.Map;

import domain.State;

public class HeuristicEvaluatorFactory {
	
	private static Map<State.Turn, HeuristicEvaluator> heuristicEvaluator;
	
	private HeuristicEvaluatorFactory() {
		heuristicEvaluator = new HashMap<State.Turn, HeuristicEvaluator>();
		heuristicEvaluator.put(State.Turn.WHITE, new WhiteHeuristicEvaluator());
		heuristicEvaluator.put(State.Turn.BLACK, new BlackHeuristicEvaluator());
	}
	
	public static HeuristicEvaluator getHeuristicEvaluator(State.Turn turn) {
		if(!heuristicEvaluator.containsKey(turn)) 
			throw new IllegalArgumentException();
		return heuristicEvaluator.get(turn);
	}
}
