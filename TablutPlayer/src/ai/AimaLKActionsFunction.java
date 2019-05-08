package ai;


import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import domain.State.Pawn;
import domain.State.Turn;
import domain.Game;
import domain.GameAshtonTablut;
import domain.State;
import domain.StateTablut;


public class AimaLKActionsFunction implements ActionsFunction {


	@Override
	public Set<Action> actions(Object arg0) {
		StateTablut currentState = null;
		Set<Action> result = new HashSet<Action>();
		Game rules = new GameAshtonTablut(99, 0, "garbage", "fake", "fake");
		if(!(arg0 instanceof StateTablut))
			throw new IllegalArgumentException();
		currentState = (StateTablut) arg0;
		Pawn[][] board = currentState.getBoard();
		for(int i=0 ; i<9; i++) {
			for(int j=0; j<9; j++) {
				// WHITE TURN
				if(currentState.getTurn().equals(Turn.WHITE)) {
					if(board[i][j].equals(Pawn.KING) || board[i][j].equals(Pawn.WHITE)) {
						result.addAll(this.selectAllActionsForThisPawn(currentState, i, j, rules));
					}
				}
				else if(currentState.getTurn().equals(Turn.BLACK))// BLACK TURN - ALTRI TURNI NON VERRANNO MAI CHIAMATI -> GUARDA CLIENT
				{
					if(board[i][j].equals(Pawn.BLACK)) {
						result.addAll(this.selectAllActionsForThisPawn(currentState, i, j, rules));
					}
				}
				else
					throw new IllegalArgumentException();
			}
		}
		return result;
	}
	
	private Set<Action> selectAllActionsForThisPawn(State currentState, int row, int column, Game rules) {
		Set<Action> result = new HashSet<Action>();	
		for(int currentRow=row+1; currentRow<9; currentRow++) {
			String from=currentState.getBox(row, column);
			String to=currentState.getBox(currentRow, column);
			domain.Action a = null;
			try {
				a = new domain.Action(from, to, currentState.getTurn());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				continue;
			}
			try {
				rules.checkMove(currentState, a);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			} 
			result.add(a);	
		}
		for(int currentRow=row-1; currentRow>=0; currentRow--) {
			String from=currentState.getBox(row, column);
			String to=currentState.getBox(currentRow, column);
			domain.Action a = null;
			try {
				a = new domain.Action(from, to, currentState.getTurn());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				continue;
			}
			try {
				rules.checkMove(currentState, a);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			} 
			result.add(a);	
		}
		for(int currentColumn=column+1; currentColumn<9; currentColumn++) {
			String from=currentState.getBox(row, column);
			String to=currentState.getBox(row, currentColumn);
			domain.Action a = null;
			try {
				a = new domain.Action(from, to, currentState.getTurn());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				continue;
			}
			try {
				rules.checkMove(currentState, a);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			} 
			result.add(a);	
		}
		for(int currentColumn=column-1; currentColumn>=0; currentColumn--) {
			String from=currentState.getBox(row, column);
			String to=currentState.getBox(row, currentColumn);
			domain.Action a = null;
			try {
				a = new domain.Action(from, to, currentState.getTurn());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				continue;
			}
			try {
				rules.checkMove(currentState, a);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			} 
			result.add(a);	
		}
		return result;
	}
}
