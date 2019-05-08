package client;


import java.io.IOException;
import java.net.UnknownHostException;
import domain.State;
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

			if(state.getTurn().equals(this.getPlayer())) {
				
			}
			// � il turno dell'avversario
			else if(!state.getTurn().equals(this.getPlayer())) {	//ERRORE!
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


	}
	



}



