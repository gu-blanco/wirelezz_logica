package usp.wirelezzgame.test;

import java.util.List;

import usp.wirelezzgame.client.ClientMessagesCallback;
import usp.wirelezzgame.core.Time;

public class ClientCallback implements ClientMessagesCallback{

	@Override
	public void nomeServer(String server) {
		System.out.println("Nome do server: "+ server);		
	}

	@Override
	public void timesData(List<Time> times) {
		// TODO Auto-generated method stub
		
	}

}
