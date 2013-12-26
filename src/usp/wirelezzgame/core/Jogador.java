package usp.wirelezzgame.core;

public class Jogador {

	private int mID;
	private String mPrimeiroNome;
	private String mNomeCompleto;
	private String mFacebookId;
	private int mTimeID;
	private int mPontosRecurso;

	public Jogador(String mPrimeiroNome, String mNomeCompleto,
			String mFacebookId) {
		this.mPrimeiroNome = mPrimeiroNome;
		this.mNomeCompleto = mNomeCompleto;
		this.mFacebookId = mFacebookId;
		this.mPontosRecurso = 5;
		this.mTimeID = 0;
	}
	public String getPrimeiroNome() {
		return mPrimeiroNome;
	}
	public void setPrimeiroNome(String mPrimeiroNome) {
		this.mPrimeiroNome = mPrimeiroNome;
	}
	public String getNomeCompleto() {
		return mNomeCompleto;
	}
	public void setNomeCompleto(String mNomeCompleto) {
		this.mNomeCompleto = mNomeCompleto;
	}
	public String getFacebookId() {
		return mFacebookId;
	}
	public void setFacebookId(String mFacebookId) {
		this.mFacebookId = mFacebookId;
	}
	public int getID() {
		return mID;
	}
	public void setID(int mID) {
		this.mID = mID;
	}
	public int getTime() {
		return mTimeID;
	}
	public void setTime(int mTimeID) {
		this.mTimeID = mTimeID;
	}
	public int getPontosRecurso(){
		return this.mPontosRecurso;
	}
	public void setPontosRecurso(int mPontosRecurso){
		this.mPontosRecurso = mPontosRecurso;
	}
	public int alterarRecurso(int valor){
		this.mPontosRecurso += valor;
		return this.mPontosRecurso;
	}	
}
