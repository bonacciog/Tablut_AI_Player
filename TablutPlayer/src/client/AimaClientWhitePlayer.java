package client;

import java.io.IOException;
import java.net.UnknownHostException;
/**
 * Tablut Client Black Player which uses Aima library
 * 
 * @author Giovanni Bonaccio
 *
 */
public class AimaClientWhitePlayer {

	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		String[] array = new String[]{"WHITE"};
		if (args.length>0){
			array = new String[]{"WHITE", args[0]};
		}
		AimaClient.main(array);

	}

}
