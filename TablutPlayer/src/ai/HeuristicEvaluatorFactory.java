package ai;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import domain.State;

public class HeuristicEvaluatorFactory {
	
	private static Map<State.Turn, HeuristicEvaluator> heuristicEvaluator = 
			Collections.unmodifiableMap(new HashMap<State.Turn, HeuristicEvaluator>() {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{ put(State.Turn.WHITE, new WhiteHeuristicEvaluator()); }
			{ put(State.Turn.BLACK, new BlackHeuristicEvaluator()); }});

	
	public static HeuristicEvaluator getHeuristicEvaluator(State.Turn turn) {
		if(!heuristicEvaluator.containsKey(turn)) 
			throw new IllegalArgumentException();
		return heuristicEvaluator.get(turn);
	}
}
