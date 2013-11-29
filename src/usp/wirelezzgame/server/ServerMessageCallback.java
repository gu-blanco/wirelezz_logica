package usp.wirelezzgame.server;

public interface ServerMessageCallback {
	
	void dadosJogador(String nomeJogador, String nomeCompleto, String facebookID);
	void timeJogador(Integer idTime);
	void interagirArea(Integer idArea, Integer latitude, Integer longitude, Integer acao);
	void responderCaptcha(Integer idCaptcha, String resposta);
	void mensagemChatTodos(String mensagem);
	void mensagemChatTime(String mensagem);

}
