package usp.wirelezzgame.server;

import java.io.IOException;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import brorlandi.server.ClientSessionInterface;

public class ServerMessageDecoder {
	
	private ServerMessageCallback mCallback;
	
	public ServerMessageDecoder(ServerMessageCallback callback){
		mCallback = callback;
	}
	
	public void parse(ClientSessionInterface client, String message){
		try{
			Object obj=JSONValue.parse(message);
			
			JSONObject json = (JSONObject) obj;
			long code = (Long) json.get("code");
			JSONAware data = (JSONAware) json.get("data");
			parseMessage(client,new Long(code).intValue(), data);
		}catch(Exception e){
			client.sendMessage(message+" | VOCE ESTA PERDIDO? ESTE SERVIDOR NAO EH PRA VOCE! CAIA FORA DAQUI!!");
			System.out.println("Bad Request: "+message);
			e.printStackTrace();
			try {
				client.getSocket().close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
	}
	
	private void parseMessage(ClientSessionInterface client, int code, JSONAware data){
		switch(code){
			case 1:
				dadosJogador(client, data);
				break;
			case 4:
				timeJogador(client, data);
				break;
			case 7:
				interagirArea(client, data);
				break;
			case 9:
				responderCaptcha(client, data);
				break;
			case 17:
				mensagemChatTodos(client, data);
				break;
			case 18:
				mensagemChatTime(client, data);
			break;
		}
	}
	
	//Recebe as informações do jogador
	public void dadosJogador(ClientSessionInterface client, JSONAware data){
		JSONObject obj = (JSONObject)data;
		
		String nomeJogador = (String) obj.get("nomeJogador");
		if(nomeJogador == null || nomeJogador.equals("null")){
			return; // bugou o cliente e enviou null
		}
		String nomeCompleto = (String) obj.get("nomeCompleto");
		String facebookID = (String) obj.get("facebookID");
		
		mCallback.dadosJogador(client, nomeJogador, nomeCompleto, facebookID);
	}
	
	//Recebe informações sobre o time que o jogador irá participar
	public void timeJogador(ClientSessionInterface client, JSONAware data){
		JSONObject obj = (JSONObject)data;
		
		int idTime = new Long((Long)obj.get("idTime")).intValue();
		
		mCallback.timeJogador(client, idTime);
	}
	
	//O jogador tentar interagir com uma área
	public void interagirArea(ClientSessionInterface client, JSONAware data){
		JSONObject obj = (JSONObject) data;
		
		int idArea = new Long((Long)obj.get("idArea")).intValue();
		double latitude = (Double) obj.get("latitude");
		double longitude = (Double) obj.get("longitude");
		int acao = new Long((Long)obj.get("acao")).intValue();
		
		mCallback.interagirArea(client, idArea, latitude, longitude, acao);		
	}
	
	//Jogador responde um captcha
	public void responderCaptcha(ClientSessionInterface client, JSONAware data){
		JSONObject obj = (JSONObject) data;
		
		int idCaptcha = new Long((Long)obj.get("idCaptcha")).intValue();
		String resposta = (String) obj.get("resposta");
		
		mCallback.responderCaptcha(client, idCaptcha, resposta);
	}
	
	//Jogador envia uma mensagem para todos
	public void mensagemChatTodos(ClientSessionInterface client, JSONAware data){
		JSONObject obj = (JSONObject) data;
		
		String mensagem = (String ) obj.get("mensagem");
		
		mCallback.mensagemChatTodos(client, mensagem);
	}
	
	//Jogador envia uma mensagem para o time
	public void mensagemChatTime(ClientSessionInterface client, JSONAware data){
		JSONObject obj = (JSONObject) data;
		
		String mensagem = (String) obj.get("mensagem");
		
		mCallback.mensagemChatTime(client, mensagem);
	}
	
}
