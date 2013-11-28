package usp.wirelezzgame.test;

import java.util.List;

import usp.wirelezzgame.client.ClientMessagesCallback;
import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.Time;

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

}
