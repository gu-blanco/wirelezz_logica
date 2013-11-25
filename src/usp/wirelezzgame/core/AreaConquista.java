package usp.wirelezzgame.core;

public class AreaConquista extends Area {
	
	private int mNivelDefesa;
	private int mDefesaAcumulada;
	
	public void setNivelDefesa(int nivelDefesa){
		this.mNivelDefesa = nivelDefesa;
	}
	
	public int getNivelDefesa(){
		return this.mNivelDefesa;
	}
	
	public void setDefesaAcumulada(int defesaAcumulada){
		this.mDefesaAcumulada = defesaAcumulada;
	}
	
	public int getDefesaAcumulada(){
		return this.mDefesaAcumulada;
	}
	
	public int alterarNivelDefesa(int valor){
		this.mNivelDefesa += valor;
		return this.mNivelDefesa;
	}
	
	public int alterarDefesaAcumulada(int valor){
		this.mDefesaAcumulada += valor;
		return this.mDefesaAcumulada;
	}
}
