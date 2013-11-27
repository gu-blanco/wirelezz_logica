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
		// TODO Auto-generated method stub
		return false;
	}

}
