package usp.wirelezzgame.test;

import usp.wirelezzgame.client.ClientMessageEncoder;
import usp.wirelezzgame.server.ServerMessageCallback;
import brorlandi.server.ClientSessionInterface;

public class ServerCallback implements ServerMessageCallback{
	
	@Override
	public void dadosJogador(ClientSessionInterface client, String nomeJogador, String nomeCompleto, String facebookID){
		System.out.println("Nome jogador: " + nomeJogador + " Nome completo: " + nomeCompleto + " Facebook ID: "+ facebookID);
	}
	
	@Override
	public void timeJogador(ClientSessionInterface client, int idTime){
		System.out.println("Id time: " + idTime);
	}
	
	@Override
	public void interagirArea(ClientSessionInterface client, int idArea, double latitude, double longitude, int acao){
		if(acao == ClientMessageEncoder.ATACAR_AREA)
			System.out.println("ATACAR Id area: " + idArea + "Latitude: " + latitude + "Longitude: " + longitude);
		else if(acao == ClientMessageEncoder.DEFENDER_AREA)
			System.out.println("DEFENDER Id area: " + idArea + "Latitude: " + latitude + "Longitude: " + longitude);
		else if(acao == ClientMessageEncoder.RECUPERAR_PONTOS_AREA)
			System.out.println("RECUPERAR PONTOS Id area: " + idArea + "Latitude: " + latitude + "Longitude: " + longitude);
	}
	
	@Override
	public void responderCaptcha(ClientSessionInterface client, int idCaptcha, String resposta){
		System.out.println("ID captcha: " + idCaptcha + "Resposta: " + resposta);
	}
	
	@Override
	public void mensagemChatTodos(ClientSessionInterface client, String mensagem){
		System.out.println("Mensagem: " + mensagem);
	}
	
	@Override
	public void mensagemChatTime(ClientSessionInterface client, String mensagem){
		System.out.println("Mensagem: " + mensagem);
	}

}
