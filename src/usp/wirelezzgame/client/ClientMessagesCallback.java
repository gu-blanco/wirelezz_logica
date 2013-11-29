package usp.wirelezzgame.client;

import java.util.List;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.Time;
import usp.wirelezzgame.core.area.Area;

public interface ClientMessagesCallback {

	void nomeServer(String server);
	void timesData(List<Time> times);
	void areasData(List<Area> areas);
	void jogadorIdTime(int idJogador, int idTime);
	void novoJogador(Jogador j);
	void mensagemCaptcha(int idCaptcha, String image);
	void mensagemResultadoCaptcha(boolean res);
	void mensagemPontosRecurso(int pontos, int modo);
	void mensagemAlteraDefesaArea(int idArea, int defesa, int acao,
			int idJogador);
	void mensagemAreaConquistada(int idArea, int defesa, int idTime,
			int idJogador);
	void mensagemVitoriaTime(int idTime);
	void mensagemJogadorDesconectou(int idJogador);
	void mensagemChat(int idJogador, int tipo, String mensagem);

}
