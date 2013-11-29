package usp.wirelezzgame.server;

public interface ServerMessageCallback {
	
	void dadosJogador(String nomeJogador, String nomeCompleto, String facebookID);
	void timeJogador(int idTime);
	void interagirArea(int idArea, double latitude, double longitude, int acao);
	void responderCaptcha(int idCaptcha, String resposta);
	void mensagemChatTodos(String mensagem);
	void mensagemChatTime(String mensagem);

}
