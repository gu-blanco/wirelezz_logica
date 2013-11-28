package usp.wirelezzgame.core.acao;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.area.AreaConquista;

public class AcaoRecuperarRecursos  extends AcaoAbstract{
	
	private AreaConquista mArea;

	public AcaoRecuperarRecursos(Jogador j, AreaConquista a) {
		super(j);
		mArea = a;
	}

	@Override
	public boolean fazerAcao() {
		if(mArea.getTimeID() == super.getJogador().getTime()){//Verifica se a base é a mesma do time do jogador
			super.getJogador().alterarRecurso(5);//Aumenta em 5 pontos de recurso do jogador
			return true;
		}
		return false;
	}

}
