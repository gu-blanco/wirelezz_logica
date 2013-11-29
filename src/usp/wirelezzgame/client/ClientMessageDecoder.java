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
			case 5:
				jogadorIdTime(data);
			break;
			case 6:
				novoJogador(data);
			break;
			case 10:
				mensagemCaptcha(data);
			break;
			case 11:
				mensagemResultadoCaptcha(data);
			break;
			case 12:
				mensagemPontosRecurso(data);
			break;
			case 13:
				mensagemAlteraDefesaArea(data);
			break;
			case 14:
				mensagemAreaConquistada(data);
			break;
			case 15:
				mensagemVitoriaTime(data);
			break;
			case 16:
				mensagemJogadorDesconectou(data);
			break;
			case 19:
				mensagemChat(data);
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

	private void jogadorIdTime(JSONAware data){
		JSONObject obj = (JSONObject)data;
		int idJogador = new Long((long) obj.get("idJogador")).intValue();
		int idTime = new Long((long) obj.get("idTime")).intValue();
		mCallback.jogadorIdTime(idJogador,idTime);
	}
	
	private void novoJogador(JSONAware data){
		JSONObject obj = (JSONObject)data;
		Jogador j = parseJogador(obj);
		int idTime = new Long((long) obj.get("idTime")).intValue();// fica faltando o time
		j.setTime(idTime);
		mCallback.novoJogador(j);
	}
	
	private void mensagemCaptcha(JSONAware data) {
		JSONObject obj = (JSONObject) data;
		int idCaptcha = new Long((long) obj.get("idCaptcha")).intValue();
		String image = (String) obj.get("imagem");
		mCallback.mensagemCaptcha(idCaptcha, image);
	}
	
	private void mensagemResultadoCaptcha(JSONAware data) {
		JSONObject obj = (JSONObject) data;
		boolean res = (boolean) obj.get("resultado");
		mCallback.mensagemResultadoCaptcha(res);
	}
	
	private void mensagemPontosRecurso(JSONAware data) {
		JSONObject obj = (JSONObject) data;
		int pontos = new Long((long) obj.get("pontos")).intValue();
		int modo = new Long((long) obj.get("modo")).intValue();
		mCallback.mensagemPontosRecurso(pontos, modo);
	}

	private void mensagemAlteraDefesaArea(JSONAware data) {
		JSONObject obj = (JSONObject) data;
		int idArea = new Long((long) obj.get("idArea")).intValue();
		int defesa = new Long((long) obj.get("defesa")).intValue();
		int acao = new Long((long) obj.get("acao")).intValue();
		int idJogador = new Long((long) obj.get("idJogador")).intValue();
		mCallback.mensagemAlteraDefesaArea(idArea, defesa, acao, idJogador);
		
	}

	private void mensagemAreaConquistada(JSONAware data) {
		JSONObject obj = (JSONObject) data;
		int idArea = new Long((long) obj.get("idArea")).intValue();
		int defesa = new Long((long) obj.get("defesa")).intValue();
		int idTime = new Long((long) obj.get("idTime")).intValue();
		int idJogador = new Long((long) obj.get("idJogador")).intValue();
		mCallback.mensagemAreaConquistada(idArea, defesa, idTime, idJogador);
	}

	private void mensagemVitoriaTime(JSONAware data) {
		JSONObject obj = (JSONObject) data;
		int idTime = new Long((long) obj.get("idTime")).intValue();
		mCallback.mensagemVitoriaTime(idTime);
	}

	private void mensagemJogadorDesconectou(JSONAware data) {
		JSONObject obj = (JSONObject) data;
		int idJogador = new Long((long) obj.get("idJogador")).intValue();
		mCallback.mensagemJogadorDesconectou(idJogador);
	}

	private void mensagemChat(JSONAware data) {
		JSONObject obj = (JSONObject) data;
		int idJogador = new Long((long) obj.get("idJogador")).intValue();
		int tipo = new Long((long) obj.get("tipo")).intValue();	
		String mensagem = (String) obj.get("mensagem");
		mCallback.mensagemChat(idJogador,tipo,mensagem);
	}
	
	

	private Area parseArea(JSONObject obj) {
		int idArea = new Long((long) obj.get("idArea")).intValue();
		double latitude = (double) obj.get("latitude");
		double longitude = (double) obj.get("longitude");
		double raio = (double) obj.get("raio");
		String tipo = (String) obj.get("tipo");
		
		Area a = null;
		
		if(tipo.equals(AreaConquista.TIPO)){
			int defesa = new Long((long) obj.get("defesa")).intValue();
			AreaConquista ac = new AreaConquista(latitude, longitude, raio, defesa);
			int idTime = new Long((long) obj.get("idTime")).intValue();
			ac.setTimeID(idTime);
			ac.setID(idArea);
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
