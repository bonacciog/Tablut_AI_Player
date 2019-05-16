package client;



import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import aima.core.search.adversarial.AlphaBetaSearch;
import domain.Action;
import domain.AimaGameAshtonTablut;
import domain.State;
import domain.State.Turn;
import domain.StateTablut;
import exceptions.ActionException;
import exceptions.BoardException;
import exceptions.CitadelException;
import exceptions.ClimbingCitadelException;
import exceptions.ClimbingException;
import exceptions.DiagonalException;
import exceptions.OccupitedException;
import exceptions.PawnException;
import exceptions.StopException;
import exceptions.ThroneException;
/**
 * Tablut Client Player which uses Aima library
 * 
 * @author Giovanni Bonaccio
 *
 */
public class AimaClient extends TablutClient {

	private int openingCount;
	private Map<StartMove, List<Action>> openingMoves;
	
	public 	enum StartMove {
		/**
		*
		*
		* Define enum for some possible opening actions for white
		*
		*/
		UP("U"), DOWN("D"), RIGHT("R"), LEFT("L");
		private final String startMove;

		private StartMove(String s) {
			startMove = s;
		}

		public boolean equalsTurn(String otherName) {
			return (otherName == null) ? false : startMove.equals(otherName);
		}

		public String toString() {
			return startMove;
		}
	}

	public AimaClient(String player, String name) throws UnknownHostException, IOException {
		super(player, name);
		openingCount = 0;
		this.openingMoves = new HashMap<StartMove, List<Action>>();
		StateTablut tmpState = new StateTablut();
		
		// Inizializzo mosse di apertura
		{
			List<Action> tmpList = new ArrayList<Action>();
			// DOWN
			try {
				tmpList.add(new Action(tmpState.getBox(6, 4), tmpState.getBox(6, 8), State.Turn.WHITE));
				tmpList.add(new Action(tmpState.getBox(5, 4), tmpState.getBox(5, 1), State.Turn.WHITE));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			openingMoves.put(StartMove.DOWN, tmpList);

		}

		{
			List<Action> tmpList = new ArrayList<Action>();

			// RIGHT
			try {
				tmpList.add(new Action(tmpState.getBox(4, 6), tmpState.getBox(0, 6), State.Turn.WHITE));
				tmpList.add(new Action(tmpState.getBox(4, 5), tmpState.getBox(7, 5), State.Turn.WHITE));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			openingMoves.put(StartMove.RIGHT, tmpList);

		}

		{
			List<Action> tmpList = new ArrayList<Action>();
			// UP
			try {
				tmpList.add(new Action(tmpState.getBox(2, 4), tmpState.getBox(2, 0), State.Turn.WHITE));
				tmpList.add(new Action(tmpState.getBox(3, 4), tmpState.getBox(3, 7), State.Turn.WHITE));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			openingMoves.put(StartMove.UP, tmpList);
		}

		{
			List<Action> tmpList = new ArrayList<Action>();
			// LEFT
			try {
				tmpList.add(new Action(tmpState.getBox(4, 2), tmpState.getBox(8, 2), State.Turn.WHITE));
				tmpList.add(new Action(tmpState.getBox(4, 3), tmpState.getBox(1, 3), State.Turn.WHITE));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			openingMoves.put(StartMove.LEFT, tmpList);
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		String role = "";
		String name = "Aima";
		AimaClient client = null;
		if (args.length < 1) {
			System.out.println("You must specify which player you are (WHITE or BLACK)");
			System.exit(-1);
		} else {
			System.out.println(args[0]);
			role = (args[0]);
		}
		if (args.length == 2) {
			name = args[1];
		}

		System.out.println("Selected client: " + args[0]);

		client = new AimaClient(role, name);
		client.run();
	}
	
	@Override
	public void run() {

		// Invio il mio nome al server

		try {
			this.declareName();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Inizializzazione partita

		State state = new StateTablut();
		state.setTurn(State.Turn.WHITE);
		System.out.println("Ashton Tablut game");
		AimaGameAshtonTablut rules = new AimaGameAshtonTablut(99, 0, "garbage", "fake", "fake");
		System.out.println("You are player " + this.getPlayer().toString() + "!");
		List<Action> openingList = this.openingMoves.get(StartMove.values()[new Random().nextInt(StartMove.values().length)]);
		AlphaBetaSearch<State, Action, State.Turn> abS = new AlphaBetaSearch<State, Action, State.Turn>(rules);
		while (true) {
			try {
				this.read();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(1);
			}
			System.out.println("Current state:");
			state = this.getCurrentState();
			System.out.println(state.toString());
			
			if (this.getPlayer().equals(Turn.WHITE)) {
				// è il mio turno
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
					this.makeDecisionAndSend(abS, state,rules, openingList);
				}
				// è il turno dell'avversario
				else if (state.getTurn().equals(StateTablut.Turn.BLACK)) {
					System.out.println("Waiting for your opponent move... ");
				}
				// ho vinto
				else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
					System.out.println("YOU WIN!");
					System.exit(0);
				}
				// ho perso
				else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
					System.out.println("YOU LOSE!");
					System.exit(0);
				}
				// pareggio
				else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
					System.out.println("DRAW!");
					System.exit(0);
				}

			} else {

				// è il mio turno
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
					this.makeDecisionAndSend(abS, state,rules, null);
				}

				else if (state.getTurn().equals(StateTablut.Turn.WHITE)) {
					System.out.println("Waiting for your opponent move... ");
				} else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
					System.out.println("YOU LOSE!");
					System.exit(0);
				} else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
					System.out.println("YOU WIN!");
					System.exit(0);
				} else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
					System.out.println("DRAW!");
					System.exit(0);
				}

			}

		}
	}
	private void makeDecisionAndSend(AlphaBetaSearch<State, Action, State.Turn> abS, State state, AimaGameAshtonTablut rules, List<Action> openingList) {
		
		Action a = null;
		// mosse apertura per bianco
		if(state.getTurn().equals(State.Turn.WHITE)) {
			if(this.openingCount<2) {
				a = openingList.get(openingCount);
				openingCount++;
				try {
					rules.checkMove(state,a);
				} catch (BoardException | ActionException | StopException | PawnException | DiagonalException
						| ClimbingException | ThroneException | OccupitedException | ClimbingCitadelException
						| CitadelException e) {
					// non puoi eseguire le mosse di apertura per violazione di regole
					a = abS.makeDecision(state);
				}
			}
			// finite le due mosse di apertura
			else
				a = abS.makeDecision(state);
		}
		else
			a = abS.makeDecision(state);
		
		try {

			this.write(a);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}







