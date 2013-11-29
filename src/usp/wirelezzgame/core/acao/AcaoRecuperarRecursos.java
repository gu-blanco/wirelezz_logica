package usp.wirelezzgame.core.acao;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.area.AreaConquista;

public class AcaoRecuperarRecursos  extends AcaoAbstract{
	
	private AreaConquista mArea;
	private AcaoCallback mCallback;

	public AcaoRecuperarRecursos(Jogador j, AreaConquista a, AcaoCallback ac) {
		super(j);
		mArea = a;
		mCallback = ac;
	}

	@Override
	public boolean fazerAcao() {
		if(mArea.getTimeID() == super.getJogador().getTime()){//Verifica se a base é a mesma do time do jogador
			super.getJogador().alterarRecurso(5);//Aumenta em 5 pontos de recurso do jogador
			mCallback.recuperouPontosRecurso(super.getJogador());
			return true;
		}
		return false;
	}

}
