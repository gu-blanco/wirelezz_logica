package usp.wirelezzgame.core.acao;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.area.AreaConquista;
import brorlandi.server.ClientSessionInterface;

public class AcaoDefenderArea extends AcaoAbstract{
	
	private AreaConquista mArea;
	private AcaoCallback mCallback;

	public AcaoDefenderArea(ClientSessionInterface client, Jogador j, AreaConquista a, AcaoCallback ac) {
		super(client,j);
		mArea = a;
		mCallback = ac;
	}

	@Override
	public boolean fazerAcao() {
		if(mArea.getTimeID() == super.getJogador().getTime()){//Verifica se a base é a mesma do time jogador
			mArea.alterarNivelDefesa(8);//Aumenta defesa em 8
			super.getJogador().alterarRecurso(-1);//Decrementa 1 ponto de recurso do jogador
			mCallback.areaDefendida(super.getClient(), mArea, super.getJogador());
			return true;
		}			
		return false;
	}
}
