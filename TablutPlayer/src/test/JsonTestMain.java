package test;

import java.io.IOException;

import com.google.gson.Gson;


import domain.LordKesaniAction;
import domain.State;
public class JsonTestMain {

	public static void main(String[] args) {
		Gson gson = new Gson();
		LordKesaniAction prova=null;
		try {
			prova = new LordKesaniAction("pr","pr",State.Turn.BLACK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(gson.toJson(prova).toString());
	}

}
