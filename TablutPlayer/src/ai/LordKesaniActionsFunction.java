package ai;


import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import domain.StateTablut;


public class LordKesaniActionsFunction implements ActionsFunction {

	@Override
	public Set<Action> actions(Object arg0) {
		StateTablut currentState = null;
		if(!(arg0 instanceof StateTablut))
			throw new IllegalArgumentException();
		currentState = (StateTablut) arg0;
		return null;
	}

}
