package usp.wirelezzgame.core.acao;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.area.Area;
import brorlandi.server.ClientSessionInterface;

public interface AcaoCallback {
	public void areaAtacada(ClientSessionInterface client, Area a, Jogador j);
	public void areaConquistada(ClientSessionInterface client, Area a, Jogador j, int timePerdedor);
	public void areaDefendida(ClientSessionInterface client, Area a, Jogador j);
	public void recuperouPontosRecurso(ClientSessionInterface client, Area a, Jogador j);
}
