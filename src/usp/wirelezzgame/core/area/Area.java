package usp.wirelezzgame.core.area;

public abstract class Area {
	protected int mID;
	protected double mLatitude;
	protected double mLongitude;
	protected double mRaio;
	protected String mNome;

	public Area(String nome, double mLatitude, double mLongitude, double mRaio) {
		this.mLatitude = mLatitude;
		this.mLongitude = mLongitude;
		this.mRaio = mRaio;
		mNome = nome;
	}

	public void setID(int ID){
		this.mID = ID;
	}
	
	public int getID(){
		return this.mID;
	}
	
	public void setLatitude(double latitude){
		this.mLatitude = latitude;
	}
	
	public double getLatitude(){
		return this.mLatitude;
	}
	
	public void setLongitude(double longitude){
		this.mLongitude = longitude;
	}
	
	public double getLongitude(){
		return this.mLongitude;
	}
	
	public void setRaio(double raio){
		this.mRaio = raio;
	}
	
	public double getRaio(){
		return this.mRaio;
	}
	
	public String getNome() {
		return mNome;
	}

	public void setNome(String mNome) {
		this.mNome = mNome;
	}
}
