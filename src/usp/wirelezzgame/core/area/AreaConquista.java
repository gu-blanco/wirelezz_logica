package usp.wirelezzgame.core.area;

public class AreaConquista extends Area {
	
	public static final String TIPO = "CONQUISTA";
	
	private int mNivelDefesa;
	private int mDefesaAcumulada;
	private int mTimeID; 
	
	public AreaConquista(String nome, double mLatitude, double mLongitude, double mRaio,
			int mNivelDefesa) {
		super(nome, mLatitude, mLongitude, mRaio);
		this.mNivelDefesa = mNivelDefesa;
		this.mDefesaAcumulada = mNivelDefesa;
		this.mTimeID = -1;
	}
	
	// A ser usado no cliente somente
	public void setNivelDefesa(int nivelDefesa){
		this.mNivelDefesa = nivelDefesa;
		mDefesaAcumulada = nivelDefesa;
	}
	
	public int getNivelDefesa(){
		return this.mNivelDefesa;
	}
	
	public synchronized int alterarNivelDefesa(int valor){
		this.mNivelDefesa += valor;
		if(mNivelDefesa > mDefesaAcumulada){
			mDefesaAcumulada = mNivelDefesa;
		}
		return this.mNivelDefesa;
	}

	public int getTimeID() {
		return mTimeID;
	}

	// deve ser chamado somente no Servidor
	public void conquistaTimeID(int mTimeID) {
		this.mTimeID = mTimeID;
		mNivelDefesa = mDefesaAcumulada/2;
		mDefesaAcumulada = mNivelDefesa;
	}

	// deve ser chamado somente no Cliente
	public void setTimeID(int idTime) {
		this.mTimeID = idTime;
		
	}
	
}
