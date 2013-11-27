package usp.wirelezzgame.core.acao;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.area.AreaConquista;

public class AcaoAtacarArea extends AcaoAbstract{

	private AreaConquista mArea;
	
	public AcaoAtacarArea(Jogador j, AreaConquista a) {
		super(j);
		mArea = a;
	}

	@Override
	public boolean fazerAcao() {
		// TODO Auto-generated method stub
		return false;
	}

}
