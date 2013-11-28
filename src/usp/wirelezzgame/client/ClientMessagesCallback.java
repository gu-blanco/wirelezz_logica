package usp.wirelezzgame.client;

import java.util.List;

import usp.wirelezzgame.core.Time;
import usp.wirelezzgame.core.area.Area;

public interface ClientMessagesCallback {

	void nomeServer(String server);
	void timesData(List<Time> times);
	void areasData(List<Area> areas);

}
