package usp.wirelezzgame.core;

import java.util.ArrayList;
import java.util.List;

public class Time {

	private List<Jogador> mJogadores;
	private Cor mCor;
	private String mNome;
	private int mID;
//	private int mAreasCapturadas;
	
	public Time(String nome, Cor cor){
		mJogadores = new ArrayList<Jogador>();
		mCor = cor;
		mNome = nome;
//		mAreasCapturadas = 0;
	}
	
	public Cor getCor(){
		return mCor;
	}
	
	public String getNome(){
		return mNome;
	}
	
	public boolean addJogador(Jogador j){
		j.setTime(mID);
		return mJogadores.add(j);
	}
	
	public int getID() {
		return mID;
	}

	public void setID(int mID) {
		this.mID = mID;
	}
	
//	public int addAreasCapturadas(){
//		return this.mAreasCapturadas++;
//	}
	
//	public int subAreasCapturadas(){
//		return this.mAreasCapturadas--;
//	}
	
	public List<Jogador> getJogadores(){
		return mJogadores;
	}
	
	public void printJogadores(){
		for(Jogador j: mJogadores){
			System.out.println(j.getID() + " : " + j.getNomeCompleto() + " , FB: "+j.getFacebookId());
		}
	}

	public enum Cor {
		VERMELHO, AZUL, AMARELO, VERDE
	}
	
	public boolean removeJogador(Jogador j){
		/*
		int aux = 0;
		while(id != mJogadores.get(aux).getID()){//Percorre a lista em busca do jogador com o mesmo ID
			aux++;
		}
		mJogadores.remove(aux);
		return true;*/
		return mJogadores.remove(j);
	}
	
}
