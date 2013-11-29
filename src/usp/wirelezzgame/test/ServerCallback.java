package usp.wirelezzgame.test;

import usp.wirelezzgame.client.ClientMessageEncoder;
import usp.wirelezzgame.server.ServerMessageCallback;

public class ServerCallback implements ServerMessageCallback{
	
	@Override
	public void dadosJogador(String nomeJogador, String nomeCompleto, String facebookID){
		System.out.println("Nome jogador: " + nomeJogador + " Nome completo: " + nomeCompleto + " Facebook ID: "+ facebookID);
	}
	
	@Override
	public void timeJogador(int idTime){
		System.out.println("Id time: " + idTime);
	}
	
	@Override
	public void interagirArea(int idArea, double latitude, double longitude, int acao){
		if(acao == ClientMessageEncoder.ATACAR_AREA)
			System.out.println("ATACAR Id area: " + idArea + "Latitude: " + latitude + "Longitude: " + longitude);
		else if(acao == ClientMessageEncoder.DEFENDER_AREA)
			System.out.println("DEFENDER Id area: " + idArea + "Latitude: " + latitude + "Longitude: " + longitude);
		else if(acao == ClientMessageEncoder.RECUPERAR_PONTOS_AREA)
			System.out.println("RECUPERAR PONTOS Id area: " + idArea + "Latitude: " + latitude + "Longitude: " + longitude);
	}
	
	@Override
	public void responderCaptcha(int idCaptcha, String resposta){
		System.out.println("ID captcha: " + idCaptcha + "Resposta: " + resposta);
	}
	
	@Override
	public void mensagemChatTodos(String mensagem){
		System.out.println("Mensagem: " + mensagem);
	}
	
	@Override
	public void mensagemChatTime(String mensagem){
		System.out.println("Mensagem: " + mensagem);
	}

}
