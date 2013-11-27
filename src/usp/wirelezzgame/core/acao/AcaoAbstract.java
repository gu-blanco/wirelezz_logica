package usp.wirelezzgame.core.acao;

import usp.wirelezzgame.core.Jogador;

public abstract class AcaoAbstract{
	
	private Jogador mJogador;
	
	public AcaoAbstract(Jogador j){
		mJogador = j;
	}
	
	public abstract boolean fazerAcao();

	public Jogador getJogador() {
		return mJogador;
	}
}
