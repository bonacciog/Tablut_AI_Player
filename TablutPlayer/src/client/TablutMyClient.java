package client;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import domain.Game;
import domain.GameAshtonTablut;
import domain.State;
import domain.State.Turn;
import domain.StateTablut;

public class TablutMyClient extends TablutClient {

	public TablutMyClient(String player, String name) throws UnknownHostException, IOException {
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
		
		/* List<int[]> pawns = new ArrayList<int[]>(); ???
		List<int[]> empty = new ArrayList<int[]>(); */

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
			
					// CODICE COPIATO DAL CLIENTE DEI PROF, PERCHE' DORME UN MINUTO? COL CAZZO
			/* try { 
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			} */
			
			if (this.getPlayer().equals(Turn.WHITE)) {
					// Sono WHITE ed è il mio turno
				
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
					
				}
			} else {
					// Sono BLACK ed è il mio turno
				
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
					
				}
			}
		
		}
	}

}
