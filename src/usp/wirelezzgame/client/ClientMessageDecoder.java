package usp.wirelezzgame.client;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.Time;
import usp.wirelezzgame.core.Time.Cor;

public class ClientMessageDecoder {
	
	private ClientMessagesCallback mCallback;
	
	public ClientMessageDecoder(ClientMessagesCallback callback){
		mCallback = callback;
	}
	
	public void parse(String message){
		Object obj=JSONValue.parse(message);
		JSONObject json = (JSONObject) obj;
		long code = (long) json.get("code");
		JSONAware data = (JSONAware) json.get("data");
		parseMessage(new Long(code).intValue(), data);
	}
	
	private void parseMessage(int code, JSONAware data){
		switch(code){
			case 0:
				nomeServer(data);
			break;
			case 2:
				timesData(data);
			break;
		}
	}
	
	private void nomeServer(JSONAware data){
		JSONObject obj = (JSONObject) data;
		String server = (String) obj.get("nomeServidor");
		mCallback.nomeServer(server);
	}
	
	private void timesData(JSONAware data){
		JSONArray timesArray = (JSONArray) data;
		List<Time> times = new ArrayList<Time>();
		Time tf;
		for(int i=0;i < timesArray.size(); i++){
			tf = parseTime((JSONObject) timesArray.get(i));
			times.add(tf);
		}
		mCallback.timesData(times);
	}

	private Time parseTime(JSONObject obj) {
		String nome = (String) obj.get("nomeTime");
		int cor = new Long((long) obj.get("cor")).intValue();
		Time t = new Time(nome, Cor.values()[cor]);
		t.setID(new Long((long) obj.get("idTime")).intValue());
		
		JSONArray jogadoresArray = (JSONArray) obj.get("jogadores");
		Jogador jogador;

		List<Jogador> js = new ArrayList<Jogador>();
		for(int i=0;i < jogadoresArray.size(); i++){
			jogador = parseJogador((JSONObject) jogadoresArray.get(i));
			js.add(jogador);
		}
		
		return t;
	}

	private Jogador parseJogador(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
