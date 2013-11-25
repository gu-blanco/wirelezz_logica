package usp.wirelezzgame.core;

public abstract class Area {
	protected int mID;
	protected double mLatitude;
	protected double mLongitude;
	protected double mRaio;
	
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
}
