package exceptions;

import domain.State;

/**
 * This exception represent a state with the wrong format
 * @author Giovanni Bonaccio
 *
 */
public class OldStateException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public OldStateException(State a)
	{
		super("The format of the state is not correct: "+a.toString());
	}
	
}
