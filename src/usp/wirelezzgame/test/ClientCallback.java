package usp.wirelezzgame.test;

import java.util.List;

import usp.wirelezzgame.client.ClientMessagesCallback;
import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.Time;
import usp.wirelezzgame.core.area.Area;
import usp.wirelezzgame.core.area.AreaConquista;
import usp.wirelezzgame.server.ServerMessageEncoder;

public class ClientCallback implements ClientMessagesCallback{

	@Override
	public void nomeServer(String server) {
		System.out.println("Nome do server: "+ server);		
	}

	@Override
	public void timesData(List<Time> times) {
		for(Time t : times){
			System.out.println("ID: "+ t.getID()+" Time: "+t.getNome());
			System.out.println("Cor: "+t.getCor());
			List<Jogador> jogadores = t.getJogadores();
			for(Jogador j : jogadores){
				System.out.println("ID: "+ j.getID()+" Jogador: "+j.getNomeCompleto()+" Facebook ID: "+ j.getFacebookId()+" Recursos: "+ j.getPontosRecurso());
			}
		}
	}

	@Override
	public void areasData(List<Area> areas) {
		for(Area aa: areas){
			AreaConquista a = (AreaConquista) aa;
			System.out.println("Area: " + a.getID()+ " Lat: " + a.getLatitude() + " Long: " + a.getLongitude() + " Raio: " + a.getRaio() + " Defesa: " + a.getNivelDefesa() + " Time: " + a.getTimeID());
		}
	}

	@Override
	public void jogadorIdTime(int idJogador, int idTime) {
		System.out.println("Jogador recebe ID: "+idJogador+ " e esta no time id: "+ idTime);
		
	}

	@Override
	public void novoJogador(Jogador j) {
		System.out.println("ID: "+ j.getID()+" Jogador: "+j.getNomeCompleto()+" Facebook ID: "+ j.getFacebookId()+" Recursos: "+ j.getPontosRecurso());		
	}

	@Override
	public void mensagemCaptcha(int idCaptcha, String image) {
		System.out.println("ID: "+ idCaptcha+" image: "+image);	
		
	}

	@Override
	public void mensagemResultadoCaptcha(boolean res) {
		if(res)
			System.out.println("Caracteres Validos!");
		else
			System.out.println("Caracteres INVALIDOS!");
		
	}

	@Override
	public void mensagemPontosRecurso(int pontos, int modo) {
		if(modo == ServerMessageEncoder.GASTOU_PONTOS)
			System.out.println("Gastou pontos agora tem: "+ pontos);
		else if(modo == ServerMessageEncoder.RECUPEROU_PONTOS)
			System.out.println("Recuperou pontos agora tem: "+ pontos);
		
	}

	@Override
	public void mensagemAlteraDefesaArea(int idArea, int defesa, int acao,
			int idJogador) {
		if(acao == ServerMessageEncoder.AREA_ATACADA)
			System.out.println("Area " + idArea + " atacada por "+ idJogador + " defesa "+defesa);
		else if(acao == ServerMessageEncoder.AREA_DEFENDIDA)
			System.out.println("Area " + idArea + " defendida por "+ idJogador + " defesa "+defesa);		
	}

	@Override
	public void mensagemAreaConquistada(int idArea, int defesa, int idTime,
			int idJogador) {
		System.out.println("Area Conquistada " + idArea + " atacada por "+ idJogador + "do time " + idTime +" defesa "+defesa);
		
	}

	@Override
	public void mensagemVitoriaTime(int idTime) {
		System.out.println("Time " + idTime + " VENCEU!");		
	}

	@Override
	public void mensagemJogadorDesconectou(int idJogador) {
		System.out.println("Jogador " + idJogador + " desconectou-se!");		
		
	}

	@Override
	public void mensagemChat(int idJogador, int tipo, String mensagem) {
		if(tipo == ServerMessageEncoder.CHAT_TODOS)
			System.out.println("Jogador " + idJogador + " mensagem todos: " + mensagem);
		else if(tipo == ServerMessageEncoder.CHAT_TIME)
			System.out.println("Jogador " + idJogador + " mensagem time: " + mensagem);	
		
	}

}
