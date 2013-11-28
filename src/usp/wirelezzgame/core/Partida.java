package usp.wirelezzgame.core;

import java.util.ArrayList;
import java.util.List;

import usp.wirelezzgame.core.area.Area;
import usp.wirelezzgame.core.area.AreaConquista;

public class Partida {
	
	private List<Time> mTimes;
	private List<Jogador> mJogadores;
	private List<Area> mAreas;
	//Guardam os últimos ID criados para jogador, time e area. Gera números de 1 até N
	private int mLastJogadorID;
	private int mLastTimeID;
	private int mLastAreaID;
	
	public Partida(){
		mTimes = new ArrayList<Time>();
		mJogadores = new ArrayList<Jogador>();
		mAreas = new ArrayList<Area>();
		mLastJogadorID = 0;
		mLastTimeID = 0;
		mLastAreaID = 0;
	}
	
	public int addTime(Time t){
		int id = mLastTimeID++;
		t.setID(id);
		mTimes.add(t);
		return id;
	}
	
	public int addJogador(Jogador j, int timeId){
		int id = mLastJogadorID++;
		Time t = mTimes.get(timeId);
		t.addJogador(j);		
		j.setID(id);
		mJogadores.add(j);
		return id;
	}
	
	public int addArea(Area a){
		int id = mLastAreaID++;
		a.setID(id);
		mAreas.add(a);
		return id;
	}

	public List<Time> getTimes(){
		return mTimes;
	}
	
	public List<Area> getAreas(){
		return mAreas;
	}
	
	public List<Jogador> getJogadores(){
		return mJogadores;
	}
	
	public Time getTimeById(int id){
		return mTimes.get(id-1);//Inicia na posição 0		
	}
	
	public Area getAreaById(int id){
		return mAreas.get(id-1);//Inicia na posição 0
	}
	
	public Jogador getJogadorById(int id){
		int aux = 0;
		while(id != mJogadores.get(aux).getID()){//Percorre a lista em busca do jogador com o mesmo ID
			aux++;
		}
		return mJogadores.get(aux);
	}
	
	//Retorna vetor inteiro onde cada posicao tem o placar de um dos times
	public int[] getPlacar(){
		int[] placar = new int[mTimes.size()];//Cada posicao representa um dos times (posicao 0 time 1, e assim por diante)
		int aux = 0;
		
		while(aux < mTimes.size()){//Inicializa placar com 0 para todas as equipes
			placar[aux] = 0;
			aux++;
		}
		
		aux = 0;
		
		while(aux < mAreas.size()){
			if(mAreas.get(aux) instanceof AreaConquista){//Verifica se é area de conquista
				AreaConquista ac = (AreaConquista)mAreas.get(aux);
				if(ac.getTimeID() != -1){//Verifica se a base já foi conquistada por algum time
					placar[ac.getTimeID()]++;//Incrementa o placar daquele time
				}					
			}
			aux++;
		}
		return placar;
	}
	
	//Método que verifica se a partida acabou (time capturou todas as bases)	
	public boolean checkFimDeJogo(){
		int[] placar;
		int aux  = 0;
		placar = getPlacar();//recebe placar
		while(aux < mAreas.size()){
			if(placar[aux] == mAreas.size()){//verifica se o time na posição aux capturou todas as bases
				return true;
			}
			aux++;
		}
		return false;		
	}
	
}
