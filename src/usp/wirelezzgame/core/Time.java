package usp.wirelezzgame.core;

import java.util.ArrayList;
import java.util.List;

public class Time {

	private List<Jogador> mJogadores;
	private Cor mCor;
	private String mNome;
	private int mID;
	private int mAreasCapturadas;
	
	public Time(String nome, Cor cor){
		mJogadores = new ArrayList<Jogador>();
		mCor = cor;
		mNome = nome;
		mAreasCapturadas = 0;
	}
	
	public Cor getCor(){
		return mCor;
	}
	
	public String getNome(){
		return mNome;
	}
	
	protected boolean addJogador(Jogador j){
		j.setTime(mID);
		return mJogadores.add(j);
	}
	
	public int getID() {
		return mID;
	}

	public void setID(int mID) {
		this.mID = mID;
	}
	
	public int addAreasCapturadas(){
		return this.mAreasCapturadas++;
	}
	
	public int subAreasCapturadas(){
		return this.mAreasCapturadas--;
	}
	
	public List<Jogador> getJogadores(){
		return mJogadores;
	}

	public enum Cor {
		VERMELHO, AZUL, AMARELO, VERDE
	}
	
}
