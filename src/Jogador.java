public class Jogador {
	private String mNome;
	private int mPontosRecurso;
	private int mTimeId;
	
	public Jogador(String nome, int pontos_recurso){
		this.mNome = nome;
		this.mPontosRecurso = pontos_recurso;
	}
	
	public void setNome(String nome){
		this.mNome = nome;
	}
	
	public String getNome(){
		return this.mNome;
	}
	
	public void setRecurso(int pontos_recurso){
		this.mPontosRecurso = pontos_recurso;
	}
	
	public int getRecurso(){
		return this.mPontosRecurso;
	}
	
	public void setTimeId(int time_id){
		this.mTimeId = time_id;
	}
	
	public int getTimeId(){
		return this.mTimeId;
	}
	
	public int subRecurso(int valor){
		this.mPontosRecurso -= valor;
		return this.mPontosRecurso;
	}
	
	public int somaRecurso(int valor){
		this.mPontosRecurso += valor;
		return this.mPontosRecurso;
	}
	
	
}
