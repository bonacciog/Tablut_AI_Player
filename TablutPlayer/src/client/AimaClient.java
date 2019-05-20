package client;



import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalTime;
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
 * Tablut Client Player which uses Aima library and can use some opening moves
 * 
 * @author Giovanni Bonaccio
 *
 */
public class AimaClient extends TablutClient {

	private static final int DEFAULTTIMEOUTINSECONDS = 50;
	/**
	 * Useful variable for opening moves
	 */
	private int openingCount;
	/**
	 * Mapping between opening move name and related actions
	 */
	private Map<StartMove, List<Action>> openingMoves;

	private int maxTimeInSeconds;
	
	boolean startWithOpeningMoves;
	
	public 	enum StartMove {
		/**
		*
		*
		* Define enum for some possible opening actions for white
		*
		*/
		GARLICKCANNON_UP("U"), GARLICKCANNON_DOWN("D"), GARLICKCANNON_RIGHT("R"), GARLICKCANNON_LEFT("L");
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
	
	public AimaClient(String player, String name, int maxTimeInSeconds, boolean startWithOpeningMoves) throws UnknownHostException, IOException {
		this(player, name, maxTimeInSeconds);
		this.startWithOpeningMoves = startWithOpeningMoves;
	}
	
	public AimaClient(String player, String name, int maxTimeInSeconds) throws UnknownHostException, IOException {
		this(player, name);
		/* tolgo un po di tempo per essere sicuro che riesca a valutare tutto l'albero
		   (relativamente alla profondita) prima del timeout*/
		if(maxTimeInSeconds>=15)
			this.maxTimeInSeconds = maxTimeInSeconds-10;
		else
			this.maxTimeInSeconds = maxTimeInSeconds;
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
			openingMoves.put(StartMove.GARLICKCANNON_DOWN, tmpList);

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
			openingMoves.put(StartMove.GARLICKCANNON_RIGHT, tmpList);

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
			openingMoves.put(StartMove.GARLICKCANNON_UP, tmpList);
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
			openingMoves.put(StartMove.GARLICKCANNON_LEFT, tmpList);
		}
	}
	/**
	 * Allows to go deep into the tree as a function of time.
	 * It was calculated after a series of tests, 
	 * through the formula of straight line passing through two points (maximum and minimum time)
	 * @param maxTime
	 * @return ottimal depth
	 */
	private int getMaxDepthForTime(int maxTime) {
			return Math.round(89+(60*(maxTime)))/119;
	}
	
	public static void main(String[] args){
		String role = "";
		String name = "LordKesani";
		int maxTime = DEFAULTTIMEOUTINSECONDS;
		boolean startWithOpeningMoves = true;
		AimaClient client = null;
		if (args.length < 1) {
			System.out.println("Parameters error");
			System.exit(-1);
		} else {
			for(String param : args) {
				if(!param.contains("=")) {
					System.out.println("Parameters error");
					System.exit(-1);
				}
				String option = param.substring(0, param.indexOf("=")+1);
				switch(option) {
				case "-P=":{
					role = param.substring(param.indexOf("=")+1);
					if(!role.equals("WHITE") && !role.equals("BLACK")){
						System.out.println("Error: WHITE or BLACK player");
						System.exit(-1);
					}
					break;
				}
				case "-N=":{
					name = param.substring(param.indexOf("=")+1);
					break;
				}
				case "-t=":{
					String time = param.substring(param.indexOf("=")+1);
					try {
						maxTime = Integer.parseInt(time);
						if(maxTime<=0) {
							throw new NumberFormatException();
						}
					}
					catch(NumberFormatException e) {
						System.out.println("You must specify an integer positive value for max time");
						System.exit(-1);
					}
					break;
				}
				case "-OM=":{
					if(param.substring(param.indexOf("=")+1).equals("NOOP"))
						startWithOpeningMoves = false;
					else {
						System.out.println("NOOP to not use opening moves.");
						System.exit(-1);
					}
					break;
				}
				default:{
					System.out.println("Parameters error");
					System.exit(-1);
				}					
				}
			}
		}
		if(role.isEmpty()) {
			System.out.println("You must specify which player you are (WHITE or BLACK)");
			System.exit(-1);
		}
		System.out.println("Selected client: " + role);

		try {
			client = new AimaClient(role, name, maxTime, startWithOpeningMoves);
		} catch (IOException e) {
			System.err.println("Errore: non riesco a raggiungere il server");
			System.exit(-1);
		}
		client.run();
	}
	
	@Override
	public void run() {

		// Invio il mio nome al server

		try {
			this.declareName();
		} catch (Exception e) {
			System.err.println("Errore: connessione col server persa");
			System.exit(-1);
		}

		// Inizializzazione partita

		State state = new StateTablut();
		state.setTurn(State.Turn.WHITE);
		System.out.println("Ashton Tablut game");

		System.out.println("You are player " + this.getPlayer().toString() + "!");
		List<Action> openingList = this.openingMoves.get(StartMove.values()[new Random().nextInt(StartMove.values().length)]);

		
		while (true) {
			try {
				this.read();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				System.err.println("Errore: connessione col server persa");
				System.exit(-1);
			}
			AimaGameAshtonTablut game = new AimaGameAshtonTablut(99, 0, "garbage", "fake", "fake", this.maxTimeInSeconds, this.getMaxDepthForTime(maxTimeInSeconds));
			game.setCurrentTime(LocalTime.now());
			System.out.println("Current state:");
			state = this.getCurrentState();
			System.out.println(state.toString());
			game.setCurrentTurn(this.getPlayer());
			game.setCurrentDepth(0);
			
			if (this.getPlayer().equals(Turn.WHITE)) {
				// è il mio turno
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
					this.makeDecisionAndSend(state,game, openingList);
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
					this.makeDecisionAndSend(state,game, null);
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
	private void makeDecisionAndSend(State state, AimaGameAshtonTablut game, List<Action> openingList) {
		AlphaBetaSearch<State, Action, State.Turn> abS = new AlphaBetaSearch<State, Action, State.Turn>(game);
		Action a = null;
		// mosse apertura per bianco
		if(state.getTurn().equals(State.Turn.WHITE)) {
			if(startWithOpeningMoves) {
				if(this.openingCount<2) {
					a = openingList.get(openingCount);
					openingCount++;
					try {
						game.checkMove(state,a);
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
		}
		else
			a = abS.makeDecision(state);
		
		try {

			this.write(a);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Errore: connessione col server persa");
			System.exit(-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Errore: connessione col server persa");
			System.exit(-1);
		}
	}
}







