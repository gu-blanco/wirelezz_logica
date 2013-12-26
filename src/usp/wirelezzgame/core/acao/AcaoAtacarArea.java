package usp.wirelezzgame.core.acao;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.area.AreaConquista;
import brorlandi.server.ClientSessionInterface;

public class AcaoAtacarArea extends AcaoAbstract{

	private AreaConquista mArea;
	private AcaoCallback mCallback;
	
	public AcaoAtacarArea(ClientSessionInterface client, Jogador j, AreaConquista a, AcaoCallback ac) {
		super(client,j);
		mArea = a;
		mCallback = ac;
	}

	@Override
	public boolean fazerAcao() {//Esse método é executado após o captcha ser aceito
		if(mArea.getTimeID() != super.getJogador().getTime()){//Verifica se o jogador está atacando uma area do time adversário
			int defesa = mArea.alterarNivelDefesa(-5);//Decrementa defesa em 5
			super.getJogador().alterarRecurso(-1);//Decrementa recursos do jogador em 1
			if(defesa <= 0){//Verifica se a area foi conquistada(nivel de defesa zerou)
				int timePerdedor = mArea.getTimeID();
				mArea.conquistaTimeID(super.getJogador().getTime());//Troca time dono da base
				mCallback.areaConquistada(super.getClient(), mArea, super.getJogador(),timePerdedor);
			}
			else{
				mCallback.areaAtacada(super.getClient(), mArea, super.getJogador());
			}
			return true;
		}
		return false;
	}

}
