package usp.wirelezzgame.core;

import java.util.ArrayList;
import java.util.List;

public class Partida {
	
	private List<Time> mTimes;
	private List<Jogador> mJogadores;
	private List<Area> mAreas;
	private int mJogadorID;
	private int mTimeID;
	private int mAreaID;
	
	public Partida(){
		mTimes = new ArrayList<Time>();
		mJogadores = new ArrayList<Jogador>();
		mAreas = new ArrayList<Area>();
		mJogadorID = 0;
		mTimeID = 0;
		mAreaID = 0;
	}
	
	public int addTime(Time t){
		int id = mTimeID++;
		t.setID(id);
		mTimes.add(t);
		return id;
	}
	
	public int addJogador(Jogador j, int timeId){
		int id = mJogadorID++;
		Time t = mTimes.get(timeId);
		t.addJogador(j);		
		j.setID(id);
		mJogadores.add(j);
		return id;
	}
	
	public int addArea(Area a){
		int id = mAreaID++;
		a.setID(id);
		mAreas.add(a);
		return id;
	}

}
