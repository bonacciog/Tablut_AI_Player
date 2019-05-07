package ai;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ResultFunction;
import domain.Game;
import domain.GameAshtonTablut;
import domain.LordKesaniAction;
import domain.State;
import domain.StateTablut;

public class LordKesaniResultFunction implements ResultFunction{

	@Override
	public Object result(Object arg0, Action arg1) {
		StateTablut currentState = null;
		if(!(arg0 instanceof StateTablut))
			throw new IllegalArgumentException();
		currentState = (StateTablut) arg0;
		Game rules = new GameAshtonTablut(99, 0, "garbage", "fake", "fake");
		State newState = null;
		try {
			newState = rules.checkMove(currentState,(LordKesaniAction) arg1);
		} catch (Exception e) {
			return newState;
		} 
		return newState;
	}

}
