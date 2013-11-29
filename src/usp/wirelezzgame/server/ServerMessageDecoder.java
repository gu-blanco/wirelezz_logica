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
				break;
			case 4:
				timeJogador(data);
				break;
			case 7:
				interagirArea(data);
				break;
			case 9:
				responderCaptcha(data);
				break;
			case 17:
				mensagemChatTodos(data);
				break;
			case 18:
				mensagemChatTime(data);
			break;
		}
	}
	
	//Recebe as informações do jogador
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
		
		int idTime = new Long((long)obj.get("idTime")).intValue();
		
		mCallback.timeJogador(idTime);
	}
	
	//O jogador tentar interagir com uma área
	public void interagirArea(JSONAware data){
		JSONObject obj = (JSONObject) data;
		
		int idArea = new Long((long)obj.get("idArea")).intValue();
		double latitude = (double) obj.get("latitude");
		double longitude = (double) obj.get("longitude");
		int acao = new Long((long)obj.get("acao")).intValue();
		
		mCallback.interagirArea(idArea, latitude, longitude, acao);		
	}
	
	//Jogador responde um captcha
	public void responderCaptcha(JSONAware data){
		JSONObject obj = (JSONObject) data;
		
		int idCaptcha = new Long((long)obj.get("idCaptcha")).intValue();
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
