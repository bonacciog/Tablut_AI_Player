package client;



import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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
 * Client per il gioco Tablut che utilizza libreria Aima
 * 
 * @author Giovanni Bonaccio
 *
 */
public class AimaClient extends TablutClient {

	private static List<State> oldStates;
	
	public AimaClient(String player, String name) throws UnknownHostException, IOException {
		super(player, name);
		oldStates = new ArrayList<State>();
	}
	
	public static boolean IsAnOldStates(State state) {
		return oldStates.contains(state);
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		String role = "";
		String name = "Aima";
		
		if (args.length < 1) {
			System.out.println("You must specify which player you are (WHITE or BLACK)");
			System.exit(-1);
		} else {
			System.out.println(args[0]);
			role = (args[0]);
		}
		if (args.length == 2) {
			name = args[2];
		}
		System.out.println("Selected client: " + args[0]);

		AimaClient client = new AimaClient(role, name);
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
		oldStates.add(state);

		System.out.println("You are player " + this.getPlayer().toString() + "!");

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
					this.makeDecisionAndSend(state);
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
					this.makeDecisionAndSend(state);
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
	private void makeDecisionAndSend(State state) {
		AimaGameAshtonTablut rules = new AimaGameAshtonTablut(99, 0, "garbage", "fake", "fake");
		AlphaBetaSearch<State, Action, State.Turn> abS = new AlphaBetaSearch<State, Action, State.Turn>(rules);
		Action a=null;
		try {
			a = abS.makeDecision(state);
			this.write(a);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oldStates.add(rules.checkMove(state, a));
		} catch (BoardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StopException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PawnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DiagonalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClimbingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ThroneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OccupitedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClimbingCitadelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CitadelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}







