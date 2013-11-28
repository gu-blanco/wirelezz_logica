package usp.wirelezzgame.server;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ServerMessageDecoder {
	
	private ServerMessageCallback mCallback;
	
	public ServerMessageDecoder(ServerMessageCallback callback){
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
			case 1:
				dadosJogador(data);
			case 4:
				timeJogador(data);
			case 7:
				interagirArea(data);
			case 9:
				responderCaptcha(data);
			case 17:
				mensagemChatTodos(data);
			case 18:
				mensagemChatTime(data);
			break;
		}
	}
	
	//Recebe as informações do jogador e retorna um objeto jogador com esses dados
	public void dadosJogador(JSONAware data){
		JSONObject obj = (JSONObject)data;
		
		String nomeJogador = (String) obj.get("nomeJogador");		
		String nomeCompleto = (String) obj.get("nomeCompleto");
		String facebookID = (String) obj.get("facebookID");
		
		mCallback.dadosJogador(nomeJogador, nomeCompleto, facebookID);
	}
	
	//Recebe informações sobre o time que o jogador irá participar
	public void timeJogador(JSONAware data){
		JSONObject obj = (JSONObject)data;
		
		Integer idTime = (Integer) obj.get("idTime");
		
		mCallback.timeJogador(idTime);
	}
	
	//O jogador tentar interagir com uma área
	public void interagirArea(JSONAware data){
		JSONObject obj = (JSONObject) data;
		
		Integer idArea = (Integer) obj.get("idArea");
		Integer latitude = (Integer) obj.get("latitude");
		Integer longitude = (Integer) obj.get("longitude");
		Integer acao = (Integer) obj.get("acao");
		
		mCallback.interagirArea(idArea, latitude, longitude, acao);		
	}
	
	//Jogador responde um captcha
	public void responderCaptcha(JSONAware data){
		JSONObject obj = (JSONObject) data;
		
		Integer idCaptcha = (Integer) obj.get("idCaptcha");
		String resposta = (String) obj.get("resposta");
		
		mCallback.responderCaptcha(idCaptcha, resposta);
	}
	
	//Jogador envia uma mensagem para todos
	public void mensagemChatTodos(JSONAware data){
		JSONObject obj = (JSONObject) data;
		
		String mensagem = (String ) obj.get("mensagem");
		
		mCallback.mensagemChatTodos(mensagem);
	}
	
	//Jogador envia uma mensagem para o time
	public void mensagemChatTime(JSONAware data){
		JSONObject obj = (JSONObject) data;
		
		String mensagem = (String) obj.get("mensagem");
		
		mCallback.mensagemChatTime(mensagem);
	}
	
}
