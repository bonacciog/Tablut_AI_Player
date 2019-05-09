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
			oldStates.add(state);
			
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
			oldStates.add(state);

		}
	}
	private void makeDecisionAndSend(State state) {
		AlphaBetaSearch<State, Action, State.Turn> abS = new AlphaBetaSearch<State, Action, State.Turn>(new AimaGameAshtonTablut(99, 0, "garbage", "fake", "fake"));
		try {
			this.write(abS.makeDecision(state));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}







