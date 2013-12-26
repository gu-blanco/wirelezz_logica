package usp.wirelezzgame.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.Time;
import usp.wirelezzgame.core.area.Area;
import usp.wirelezzgame.core.area.AreaConquista;
import usp.wirelezzgame.core.captcha.Captcha;
import brorlandi.server.Server;
import brorlandi.server.ServerCallbackInterface;
import brorlandi.server.ServerInterface;

public class WirelezzServer implements ServerCallbackInterface{
	
	private List<Time> mTimes;
	private List<Jogador> mJogadores;
	private List<Area> mAreas;
	private List<Captcha> mCaptchas;
	//Guardam os últimos ID criados para jogador, time e area. Gera números de 1 até N
	private int mLastJogadorID;
	private int mLastTimeID;
	private int mLastAreaID;
	private int mLastCaptchaID;
	private int[] mPlacar;
	
	//Servidor
	private int mPort;
	private ServerInterface mServer;
	
	// Thread Lockers
	public Object lockTimes;
	public Object lockJogadores;
	public Object lockAreas;
	public Object lockCaptchas;
	public Object lockPlacar;
	
    Timer mRoundTimer;
    Timer mTimer;
	
	public WirelezzServer(){
		mRoundTimer = null;
		mTimes = new ArrayList<Time>();
		mJogadores = new ArrayList<Jogador>();
		mAreas = new ArrayList<Area>();
		mCaptchas = new ArrayList<Captcha>();
		mLastJogadorID = 0;
		mLastTimeID = 0;
		mLastAreaID = 0;
		mLastCaptchaID = 0;
		
		// inicializa lockers
		lockTimes = new Object();
		lockJogadores = new Object();
		lockAreas = new Object();
		lockCaptchas = new Object();
		lockPlacar = new Object();
	}
	
	public static void main(String[] args) {
		WirelezzServer ws = new WirelezzServer();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Digite a porta do Servidor: ");
			int porta = Integer.parseInt(br.readLine());
			ws.setPort(porta);
			System.out.println("Digite o número de Times: ");
			int ntimes = Integer.parseInt(br.readLine());
			ws.addNewTime(new Time("Alpha", Time.Cor.VERMELHO));
			ws.addNewTime(new Time("Bravo", Time.Cor.AZUL));
			if(ntimes >= 3){
				ws.addNewTime(new Time("Charlie", Time.Cor.VERDE));
			}
			if(ntimes >= 4){
				ws.addNewTime(new Time("Delta", Time.Cor.AMARELO));
			}			
			System.out.println("Digite o número de Áreas: ");
			int nareas = Integer.parseInt(br.readLine());
			
			int count = 0;
			double lat, lng, raio;
			String nome;
			while(count < nareas){
				System.out.println("Digite o NOME da Área "+count);
				nome = br.readLine();
				System.out.println("Digite a LATITUDE da Área "+count);
				lat = Double.parseDouble(br.readLine());
				System.out.println("Digite a LONGITUDE da Área "+count);
				lng = Double.parseDouble(br.readLine());
				System.out.println("Digite a RAIO da Área "+count);
				raio = Double.parseDouble(br.readLine());
				ws.addNewArea(new AreaConquista(nome,lat, lng, raio, 5));
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ws.criarPlacar();
		
		System.out.println("Iniciando servidor...");
		
		//ServerInputKeyboard input = new ServerInputKeyboard(ws);
		WirelezzClientSession cs = new WirelezzClientSession(ws,"The Wirelezz Game Server! - Conectado!");
		//new Server(ws, cs,input);
		new Server(ws, cs, null);
	}
	
	private void setPort(int port){
		mPort = port;
	}

	// Adiciona no servidor
	public int addNewTime(Time t){
		int id;
		synchronized (lockTimes) {
			id = mLastTimeID++;
			t.setID(id);
			mTimes.add(t);	
		}
		return id;
	}

	// Adiciona no servidor
	public int addNewCaptcha(Captcha c){
		int id;
		synchronized (lockCaptchas) {
			id = mLastCaptchaID++;
			c.setID(id);
			mCaptchas.add(c);	
		}
		return id;
	}

	// Adiciona no servidor
	public int addNewJogador(Jogador j, int timeId){
		int id;
		synchronized (lockJogadores) {
			id  = mLastJogadorID++;
			Time t = mTimes.get(timeId);
			t.addJogador(j);		
			j.setID(id);
			mJogadores.add(j);
		}
		return id;
	}
	
	// Adiciona no servidor
	public int addNewArea(Area a){
		int id;
		synchronized (lockAreas) {
			id = mLastAreaID++;
			a.setID(id);
			mAreas.add(a);
		}
		return id;
	}

	// Apenas no cliente, quando recebe os dados dos times e jogadores
	/*
	public void setTimes(List<Time> lt){
		mTimes = lt;
		for(Time t:lt){
			List<Jogador> js = t.getJogadores();
			for(Jogador j : js){
				mJogadores.add(j);
			}
		}
	}*/

	// Apenas no cliente, quando recebe os dados das areas
	public void setAreas(List<Area> la){
		mAreas = la;
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
		return mTimes.get(id);
	}
	
	public Area getAreaById(int id){
		return mAreas.get(id);
	}
	
	public Jogador getJogadorById(int id){
		int aux = 0;
		while(id != mJogadores.get(aux).getID()){//Percorre a lista em busca do jogador com o mesmo ID
			aux++;
		}
		return mJogadores.get(aux);
	}
	
	public Captcha getCaptchaById(int id){
		int aux = 0;
		while(id != mCaptchas.get(aux).getID()){//Percorre a lista em busca do Captcha com o ID;
			aux++;
		}
		return mCaptchas.get(aux);
	}
	
	public boolean removeCaptcha(Captcha c){
		return mCaptchas.remove(c);
	}
	
	public boolean removeJogador(Jogador j){
		boolean b;
		synchronized (lockJogadores) {
			b = mJogadores.remove(j);
			if(b){
				b = getTimeById(j.getTime()).removeJogador(j);
			}
		}
		return b;
	}
	
	//Cria vetor de inteiros onde cada posicao tem o placar de um dos times
	private void criarPlacar(){
		mPlacar = new int[mTimes.size()];//Cada posicao representa um dos times (posicao 0 time 1, e assim por diante)
		int aux = 0;
		
		while(aux < mTimes.size()){//Inicializa placar com 0 para todas as equipes
			mPlacar[aux] = 0;
			aux++;
		}
		/*
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
		*/
	}
	
	//Método que verifica se a partida acabou. Deve receber os pontos de um time que acabou de capturar uma area.
	public boolean checkFimDeJogo(int ganhador, int pontos){
		int countAreas = 0;
		for(Area a : mAreas){
			if(a instanceof AreaConquista){
				countAreas++;
			}
		}
		//int time = -1;
		if(pontos == countAreas){
			/*
			int ntimes = mTimes.size();
			for(int i=0; i<ntimes;i++){
				if(mPlacar[i] == countAreas){
					time = i;
					break;
				}
			}
			*/
			Time t = getTimeById(ganhador);
			String m = ServerMessageEncoder.mensagemVitoriaTime(t);
			System.out.println("Time "+t.getNome() + " VENCEU A PARTIDA! Jogadores:");
			t.printJogadores();
			mServer.sendMessageToAll(m);
			setTimer(10);
		}
		return false;		
	}
	
	public void alterarPlacar(int ganhador, int perdedor){
		synchronized (lockPlacar) {
			mPlacar[ganhador]++;
			if(perdedor != -1){
				mPlacar[perdedor]--;
			}
			checkFimDeJogo(ganhador,mPlacar[ganhador]);
		}		
	}


    public void setTimer(int seconds) {
    	mTimer = new Timer();
    	mTimer.schedule(new Task(mServer), seconds*1000);
    }
    
    public void setRoundTimer(int seconds) {
    	if(mRoundTimer == null){
    	mRoundTimer = new Timer();
    	mTimer.schedule(new RoundEnd(this), seconds*1000);
    	}
    }

    class RoundEnd extends TimerTask {
    	WirelezzServer mserver;
    	
        public RoundEnd(WirelezzServer mServer) {
        	mserver = mServer;
		}

		public void run() {
            mRoundTimer.cancel(); //Terminate the timer thread
            System.out.println("Terminou o Round!");
            mserver.checkFimDeRound();
            //System.exit(0);
        }
    }
    
    public boolean checkFimDeRound(){
    	int len = mPlacar.length;
    	int maior = -1;
    	int idMaior = -1;
    	for(int i=0;i<len;i++){
    		if(mPlacar[i] > maior){
    			maior = mPlacar[i];
    			idMaior = i;
    		}
    	}
    	int count = 0;
    	for(int i=0;i<len;i++){
    		if(mPlacar[i] == maior){
    			count++;
    		}
    	}
    	if(count > 1){
    		setRoundTimer(60);
    		return false;
    	}
    	int ganhador = idMaior;
    	
		Time t = getTimeById(ganhador);
		String m = ServerMessageEncoder.mensagemVitoriaTime(t);
		System.out.println("Time "+t.getNome() + " VENCEU A PARTIDA! Jogadores:");
		t.printJogadores();
		mServer.sendMessageToAll(m);
		setTimer(10);
		return true;		
	}

    class Task extends TimerTask {
    	ServerInterface mserver;
    	
        public Task(ServerInterface mServer) {
        	mserver = mServer;
		}

		public void run() {
            mTimer.cancel(); //Terminate the timer thread
            mserver.downServer();
            System.out.println("Fechou Servidor!");
           
            System.exit(0);
        }
    }
	
	@Override
	public int getPort() {
		return mPort;
	}

	@Override
	public void onException(Exception e) {
		System.out.println("Server Exception Message: " + e.getMessage());
		e.printStackTrace();		
	}

	@Override
	public void onServerClosesByException(Exception e) {
		System.out.println("Server Closed by Exception!!!! Message: " + e.getMessage());
		e.printStackTrace();				
	}

	@Override
	public void onServerInput(String s) {
		try{
		if(s.equals("fechar")){
			mServer.downServer();
			setTimer(3);
		}
		else if(s.equals("placar")){
			System.out.println("Placar:");
			for(int i=0;i<mPlacar.length;i++){
				Time t = getTimeById(i);
				System.out.println(t.getNome() + " : "+ mPlacar[i] + " áreas.");
			}
			System.out.println("----");
		}
		else if(s.equals("jogadores")){
			System.out.println("Jogadores: "+ mJogadores.size());
			for(Jogador j : mJogadores){
				System.out.println(j.getID()+ " : "+ j.getNomeCompleto()+" , FB: "+j.getFacebookId()+" , "+getTimeById(j.getTime()).getNome()+" , Recursos: "+j.getPontosRecurso());
			}
			System.out.println("----");
		}
		else if(s.equals("areas")){
			System.out.println("Areas: "+ mAreas.size());
			int time;
			for(Area a : mAreas){
				AreaConquista ac = (AreaConquista) a;
				time = ac.getTimeID();
				if(time != -1)
					System.out.println(a.getID()+ " : "+ a.getNome()+" : "+ a.getLatitude() +" , "+a.getLongitude()+" , "+getTimeById(ac.getTimeID()).getNome()+" , Def: "+ac.getNivelDefesa());
				else
					System.out.println(a.getID()+ " : "+ a.getNome()+" : "+ a.getLatitude() +" , "+a.getLongitude()+" , Sem Time , Def: "+ac.getNivelDefesa());
			}
			System.out.println("----");
		}
		else if(s.equals("captchas")){
			System.out.println("Captchas Ativos: "+ mCaptchas.size());
			for(Captcha c : mCaptchas){
				System.out.println(c.getID()+ " : "+ c.getAcao().getJogador().getNomeCompleto());
			}
			System.out.println("----");
		}
		else{
			System.out.println("Comandos:");
			System.out.println("fechar: Fechar o servidor.");
			System.out.println("placar");
			System.out.println("jogadores");
			System.out.println("areas");
			System.out.println("captchas");
		}}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onServerOff(ServerInterface server) {
		System.out.println("Fechando Servidor.");
		//System.exit(1);	
	}

	@Override
	public void onServerOn(ServerInterface server) {
		mServer = server;
		try {
			System.out.println("Wirelezz Game aberto: "+InetAddress.getLocalHost().getHostAddress() +":"+server.getLocalPort());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}		
	}
	
}
