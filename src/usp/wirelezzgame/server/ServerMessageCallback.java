package usp.wirelezzgame.server;

import brorlandi.server.ClientSessionInterface;

public interface ServerMessageCallback {
	
	void dadosJogador(ClientSessionInterface client, String nomeJogador, String nomeCompleto, String facebookID);
	void timeJogador(ClientSessionInterface client, int idTime);
	void interagirArea(ClientSessionInterface client, int idArea, double latitude, double longitude, int acao);
	void responderCaptcha(ClientSessionInterface client, int idCaptcha, String resposta);
	void mensagemChatTodos(ClientSessionInterface client, String mensagem);
	void mensagemChatTime(ClientSessionInterface client, String mensagem);

}
