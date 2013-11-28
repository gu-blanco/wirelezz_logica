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
	public boolean fazerAcao() {//Esse método é executado após o captcha ser aceito
		if(mArea.getTimeID() != super.getJogador().getTime()){//Verifica se o jogador está atacando uma area do time adversário
			int defesa = mArea.alterarNivelDefesa(-5);//Decrementa defesa em 5
			super.getJogador().alterarRecurso(-1);//Decrementa recursos do jogador em 1
			if(defesa <= 0){//Verifica se a area foi conquistada(nivel de defesa zerou)
				mArea.setTimeID(super.getJogador().getTime());//Troca time dono da base
			}
			return true;
		}
		return false;
	}

}
