package usp.wirelezzgame.test;

import usp.wirelezzgame.server.ServerMessageCallback;

public class ServerCallback implements ServerMessageCallback{
	
	@Override
	public void dadosJogador(String nomeJogador, String nomeCompleto, String facebookID){
		System.out.println("Nome jogador: " + nomeJogador + "Nome completo: " + nomeCompleto + "Facebook ID: "+ facebookID);
	}
	
	@Override
	public void timeJogador(Integer idTime){
		System.out.println("Id time: " + idTime);
	}
	
	@Override
	public void interagirArea(Integer idArea, Integer latitude, Integer longitude, Integer acao){
		System.out.println("Id area: " + idArea + "Latitude: " + latitude + "Longitude: " + longitude + "Acao: " + acao);
	}
	
	@Override
	public void responderCaptcha(Integer idCaptcha, String resposta){
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
