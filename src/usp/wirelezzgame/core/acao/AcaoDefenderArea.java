package usp.wirelezzgame.core.acao;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.area.AreaConquista;

public class AcaoDefenderArea extends AcaoAbstract{
	
	private AreaConquista mArea;

	public AcaoDefenderArea(Jogador j, AreaConquista a) {
		super(j);
		mArea = a;
	}

	@Override
	public boolean fazerAcao() {
		if(mArea.getTimeID() == super.getJogador().getTime()){//Verifica se a base é a mesma do time jogador
			int defesa = mArea.alterarNivelDefesa(8);//Aumenta defesa em 8
			super.getJogador().alterarRecurso(-1);//Decrementa 1 ponto de recurso do jogador
			return true;
		}			
		return false;
	}
}
