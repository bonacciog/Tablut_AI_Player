package client;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import domain.Action;
import domain.Game;
import domain.GameAshtonTablut;
import domain.State;
import domain.State.Turn;
import domain.StateTablut;

public class LordKesaniClient extends TablutClient {

	public LordKesaniClient(String player, String name) throws UnknownHostException, IOException {
		super(player, name);
		// TODO Auto-generated constructor stub
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
		Game rules = new GameAshtonTablut(99, 0, "garbage", "fake", "fake");
		System.out.println("Ashton Tablut game");

		List<int[]> pawns = new ArrayList<int[]>();
		List<int[]> empty = new ArrayList<int[]>();

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
				// Sono WHITE ed è il mio turno

				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
					if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
						/*// Raccolgo le posizioni delle mie pedine in pawns e le posizioni vuote in empty

						int[] buf;
						for (int i = 0; i < state.getBoard().length; i++) {
							for (int j = 0; j < state.getBoard().length; j++) {
								if (state.getPawn(i, j).equalsPawn(State.Pawn.WHITE.toString())
										|| state.getPawn(i, j).equalsPawn(State.Pawn.KING.toString())) {
									buf = new int[2];
									buf[0] = i;
									buf[1] = j;
									pawns.add(buf);
								} else if (state.getPawn(i, j).equalsPawn(State.Pawn.EMPTY.toString())) {
									buf = new int[2];
									buf[0] = i;
									buf[1] = j;
									empty.add(buf);
								}
							}
						}
						*/
						Action a = null;
						/*int[] selected = null;

						boolean found = false;
					
						while (!found) {
							if (pawns.size() > 1) {
								//	selected = pawns.get(new Random().nextInt(pawns.size() - 1)); !!!!!!!!
							} else {
								selected = pawns.get(0);
							}

							String from = this.getCurrentState().getBox(selected[0], selected[1]);

							// selected = empty.get(new Random().nextInt(empty.size() - 1));  !!!!!!!!!
							String to = this.getCurrentState().getBox(selected[0], selected[1]);

							try {
								a = new Action(from, to, State.Turn.WHITE);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							try {
								rules.checkMove(state, a);
								found = true;
							} catch (Exception e) {

							}

						}*/
						System.out.println("Mossa scelta: " + a.toString());
						try {
							this.write(a);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						pawns.clear();
						empty.clear();

					}
					// � il turno dell'avversario
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
				}
			} else {
				// Sono BLACK ed è il mio turno

				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
					// Raccolgo le posizioni delle mie pedine in pawns e le posizioni vuote in empty
					/*
					int[] buf;
					for (int i = 0; i < state.getBoard().length; i++) {
						for (int j = 0; j < state.getBoard().length; j++) {
							if (state.getPawn(i, j).equalsPawn(State.Pawn.BLACK.toString())) {
								buf = new int[2];
								buf[0] = i;
								buf[1] = j;
								pawns.add(buf);
							} else if (state.getPawn(i, j).equalsPawn(State.Pawn.EMPTY.toString())) {
								buf = new int[2];
								buf[0] = i;
								buf[1] = j;
								empty.add(buf);
							}
						}
					}
					*/
					
					Action a = null;
					/*int[] selected = null;

					boolean found = false;
					
					while (!found) {
						selected = pawns.get(new Random().nextInt(pawns.size() - 1));       !!!!!!
						String from = this.getCurrentState().getBox(selected[0], selected[1]);

						selected = empty.get(new Random().nextInt(empty.size() - 1));       !!!!!!
						String to = this.getCurrentState().getBox(selected[0], selected[1]);

						try {
							a = new Action(from, to, State.Turn.BLACK);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						System.out.println("try: " + a.toString());
						try {
							rules.checkMove(state, a);
							found = true;
						} catch (Exception e) {

						}
							
					}*/

					System.out.println("Mossa scelta: " + a.toString());
					try {
						this.write(a);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pawns.clear();
					empty.clear();

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
}


