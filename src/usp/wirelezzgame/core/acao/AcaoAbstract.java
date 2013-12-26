package usp.wirelezzgame.core.acao;

import usp.wirelezzgame.core.Jogador;
import brorlandi.server.ClientSessionInterface;

public abstract class AcaoAbstract{

	private Jogador mJogador;
	private ClientSessionInterface mClient;
	
	public AcaoAbstract(ClientSessionInterface client, Jogador j){
		mJogador = j;
		mClient = client;
	}
	
	public abstract boolean fazerAcao();

	public Jogador getJogador() {
		return mJogador;
	}

	public ClientSessionInterface getClient() {
		return mClient;
	}
}
