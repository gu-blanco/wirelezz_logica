package usp.wirelezzgame.core.acao;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.area.Area;

public interface AcaoCallback {
	public void areaAtacada(Area a, Jogador j);
	public void areaConquistada(Area a, Jogador j);
	public void areaDefendida(Area a, Jogador j);
	public void recuperouPontosRecurso(Jogador j);
}
