package domain;

import java.io.IOException;

import domain.State.Turn;

public class AIMAAction extends domain.Action{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AIMAAction(String from, String to, Turn t) throws IOException {
		super(from, to, t);
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean isNoOp() {
		// TODO Auto-generated method stub
		return false;
	}

}
