package client;

import java.io.IOException;
import java.net.UnknownHostException;
/**
 * Tablut Client Black Player which uses Aima library
 * 
 * @author Giovanni Bonaccio
 *
 */
public class AimaClientBlackPlayer {

	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		String[] array = new String[]{"-P=BLACK"};
		AimaClient.main(array);

	}

}
