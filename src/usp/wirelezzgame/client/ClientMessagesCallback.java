package usp.wirelezzgame.client;

import java.util.List;

import usp.wirelezzgame.core.Time;

public interface ClientMessagesCallback {

	void nomeServer(String server);
	void timesData(List<Time> times);

}
