package client;

import java.io.IOException;
import java.net.UnknownHostException;

public class AimaClientBlackPlayer {

	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		String[] array = new String[]{"BLACK"};
		if (args.length>0){
			array = new String[]{"BLACK", args[0]};
		}
		AimaClient.main(array);

	}

}
