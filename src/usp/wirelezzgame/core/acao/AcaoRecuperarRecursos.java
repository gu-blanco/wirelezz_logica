package usp.wirelezzgame.core.acao;

import brorlandi.server.ClientSessionInterface;
import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.area.AreaConquista;

public class AcaoRecuperarRecursos  extends AcaoAbstract{
	
	private AreaConquista mArea;
	private AcaoCallback mCallback;

	public AcaoRecuperarRecursos(ClientSessionInterface client, Jogador j, AreaConquista a, AcaoCallback ac) {
		super(client,j);
		mArea = a;
		mCallback = ac;
	}

	@Override
	public boolean fazerAcao() {
		if(mArea.getTimeID() == super.getJogador().getTime()){//Verifica se a base é a mesma do time do jogador
			super.getJogador().alterarRecurso(5);//Aumenta em 5 pontos de recurso do jogador
			mCallback.recuperouPontosRecurso(super.getClient(),mArea, super.getJogador());
			return true;
		}
		return false;
	}

}