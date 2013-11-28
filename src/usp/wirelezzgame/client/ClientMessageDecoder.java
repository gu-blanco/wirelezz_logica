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
import usp.wirelezzgame.core.area.Area;
import usp.wirelezzgame.core.area.AreaConquista;

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
			case 3:
				areasData(data);
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
	
	private void areasData(JSONAware data){
		JSONArray areasArray = (JSONArray) data;
		List<Area> areas = new ArrayList<Area>();
		Area a;
		for(int i=0;i < areasArray.size(); i++){
			a = parseArea((JSONObject) areasArray.get(i));
			areas.add(a);
		}
		mCallback.areasData(areas);
	}

	private Area parseArea(JSONObject obj) {
		int idArea = new Long((long) obj.get("idArea")).intValue();
		double latitude = (double) obj.get("latitude");
		double longitude = (double) obj.get("longitude");
		double raio = (double) obj.get("raio");
		String tipo = (String) obj.get("tipo");
		
		Area a;
		
		if(tipo.equals(AreaConquista.TIPO)){
			int defesa = new Long((long) obj.get("defesa")).intValue();
			AreaConquista ac = new AreaConquista(latitude, longitude, raio, defesa);
			int idTime = new Long((long) obj.get("idTime")).intValue();
			ac.setTimeID(idTime);
			a = (Area) ac;
		}		
		
		return a;
	}

	private Time parseTime(JSONObject obj) {
		String nome = (String) obj.get("nomeTime");
		int cor = new Long((long) obj.get("cor")).intValue();
		Time t = new Time(nome, Cor.values()[cor]);
		t.setID(new Long((long) obj.get("idTime")).intValue());
		
		JSONArray jogadoresArray = (JSONArray) obj.get("jogadores");
		Jogador jogador;

		for(int i=0;i < jogadoresArray.size(); i++){
			jogador = parseJogador((JSONObject) jogadoresArray.get(i));
			t.addJogador(jogador);
		}
		return t;
	}

	private Jogador parseJogador(JSONObject obj) {
		Jogador j;
		int id = new Long((long) obj.get("idJogador")).intValue();
		String nome = (String) obj.get("nomeJogador");
		String nomeCompleto = (String) obj.get("nomeCompleto");
		String fbid = (String) obj.get("facebookID");
		j = new Jogador(nome, nomeCompleto, fbid);
		j.setID(id);
		return j;
	}

}
