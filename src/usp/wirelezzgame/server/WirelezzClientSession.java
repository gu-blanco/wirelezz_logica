package usp.wirelezzgame.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import usp.wirelezzgame.client.ClientMessageEncoder;
import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.acao.AcaoAbstract;
import usp.wirelezzgame.core.acao.AcaoAtacarArea;
import usp.wirelezzgame.core.acao.AcaoCallback;
import usp.wirelezzgame.core.acao.AcaoDefenderArea;
import usp.wirelezzgame.core.acao.AcaoRecuperarRecursos;
import usp.wirelezzgame.core.area.Area;
import usp.wirelezzgame.core.area.AreaConquista;
import usp.wirelezzgame.core.captcha.Captcha;
import brorlandi.server.ClientSessionCallbackInterface;
import brorlandi.server.ClientSessionInterface;
import brorlandi.server.ServerInterface;

public class WirelezzClientSession implements ClientSessionCallbackInterface, ServerMessageCallback, AcaoCallback{
	

	private HashMap<ClientSessionInterface, Jogador> mClientJogador;
	private ArrayList<Jogador> mDesconectados;
	
	//Servidor
	private ServerInterface mServerInterface;
	private WirelezzServer mWirelezzServer;
	
	private ServerMessageDecoder mDecoder;
	private String mHelloMessage;

	public WirelezzClientSession(WirelezzServer ws, String helloMessage) {
		mWirelezzServer = ws;
		mClientJogador = new HashMap<ClientSessionInterface, Jogador>();
		mDesconectados = new ArrayList<Jogador>();
		mDecoder = new ServerMessageDecoder(this);
		mHelloMessage = helloMessage;
	}
	
	public void setHelloMessage(String m){
		mHelloMessage = m;
	}
	
	public String getHelloMessage(){
		return mHelloMessage;
	}

	@Override
	public void onClientSessionConnect(ClientSessionInterface client) {
		client.sendMessage(ServerMessageEncoder.nomeServer(mHelloMessage));
		System.out.println("Novo Cliente: "+client.getSocket().getInetAddress().getHostAddress() + ":"+client.getSocket().getPort());
		
	}
	
	private Jogador findDesconectado(String facebook){
		for(Jogador j : mDesconectados){
			if(j.getFacebookId().equals(facebook)){
				mDesconectados.remove(j);
				return j;
			}
		}
		return null;
	}

	@Override
	public void onClientSessionDisconnect(ClientSessionInterface client) {
		Jogador j = mClientJogador.get(client);
		if(j != null){
			mDesconectados.add(j);
			mWirelezzServer.removeJogador(j);
			mServerInterface.sendMessageToAll(ServerMessageEncoder.mensagemJogadorDesconectou(j));
			System.out.println(j.getNomeCompleto() + " (" + mWirelezzServer.getTimeById(j.getTime()).getNome()+") desconectado: "+client.getSocket().getInetAddress().getHostAddress() + ":"+client.getSocket().getPort());
		}
		else
			System.out.println("Cliente Desconectado: "+client.getSocket().getInetAddress().getHostAddress() + ":"+client.getSocket().getPort());
	}

	@Override
	public void onException(ClientSessionInterface client, Exception e) {
		Jogador j = mClientJogador.get(client);
		if(j != null){
			System.out.println("Client Session Exception Jogador: " + j.getNomeCompleto());
		}		
		System.out.println("Client Session Exception Message: " + e.getMessage());
		e.printStackTrace();				
	}

	@Override
	public void onMessageReceive(ClientSessionInterface client, String str) {
		mDecoder.parse(client, str);		
	}

	@Override
	public void onServerOff(ServerInterface server) {
		
	}

	@Override
	public void onServerOn(ServerInterface server) {
		mServerInterface = server;
		
	}
/*
	@Override
	public void dadosJogador(String nomeJogador, String nomeCompleto,
			String facebookID) {
		
	}
*/

	@Override
	public void dadosJogador(ClientSessionInterface client, String nomeJogador,
			String nomeCompleto, String facebookID) {
		Jogador j = new Jogador(nomeJogador, nomeCompleto, facebookID);
		System.out.println(client.getSocket().getInetAddress().getHostAddress() + ":"+client.getSocket().getPort()+", "+nomeCompleto+", FB: "+facebookID);
		mClientJogador.put(client, j);	
		client.sendMessage(ServerMessageEncoder.timesData(mWirelezzServer.getTimes()));
		client.sendMessage(ServerMessageEncoder.areasData(mWirelezzServer.getAreas()));
	}

	@Override
	public void timeJogador(ClientSessionInterface client, int idTime) {
		Jogador j = mClientJogador.get(client);
		Jogador f = findDesconectado(j.getFacebookId());
		if(f != null){
			System.out.println(j.getNomeCompleto() + " esta voltando ao servidor, atribuindo pontos de recurso = "+f.getPontosRecurso());
			j.setPontosRecurso(f.getPontosRecurso());
			client.sendMessage(ServerMessageEncoder.mensagemPontosRecurso(j, ServerMessageEncoder.RECUPEROU_PONTOS));	
		}
		if(j != null){
			int id = mWirelezzServer.addNewJogador(j, idTime);
			System.out.println(id + " : "+j.getPrimeiroNome() + " entrou no Time " + mWirelezzServer.getTimeById(idTime).getNome());
			client.sendMessage(ServerMessageEncoder.jogadorIdTime(j));
			mServerInterface.sendMessageToAll(ServerMessageEncoder.novoJogador(j));
		}	
	}

	@Override
	public void interagirArea(ClientSessionInterface client, int idArea,
			double latitude, double longitude, int acao) {
		Jogador j = mClientJogador.get(client);
		AreaConquista ac = (AreaConquista) mWirelezzServer.getAreaById(idArea);
		AcaoAbstract aa = null;
		switch(acao){
		case ClientMessageEncoder.ATACAR_AREA:
			aa = new AcaoAtacarArea(client, j, ac, this);
		break;
		case ClientMessageEncoder.DEFENDER_AREA:
			aa = new AcaoDefenderArea(client, j, ac, this);
		break;
		case ClientMessageEncoder.RECUPERAR_PONTOS_AREA:
			aa = new AcaoRecuperarRecursos(client, j, ac, this);
		break;
		}
		Captcha c = new Captcha(aa);
		mWirelezzServer.addNewCaptcha(c);
		System.out.println(j.getPrimeiroNome() + " recebeu o captcha " + c.getID());
		client.sendMessage(ServerMessageEncoder.mensagemCaptcha(c));
	}

	@Override
	public void responderCaptcha(ClientSessionInterface client, int idCaptcha,
			String resposta) {
		Captcha c = mWirelezzServer.getCaptchaById(idCaptcha);
		boolean b = false;
		try {
			b = c.respostaCaptcha(resposta);
		} catch (Exception e) {
			this.onException(client, e);
		}
		mWirelezzServer.removeCaptcha(c);
		client.sendMessage(ServerMessageEncoder.mensagemResultadoCaptcha(b));
		if(b){
			System.out.println(mClientJogador.get(client).getPrimeiroNome() + " respondeu CORRETO ao captcha " + idCaptcha);
			c.getAcao().fazerAcao();
		}else{
			System.out.println(mClientJogador.get(client).getPrimeiroNome() + " respondeu ERRADO ao captcha " + idCaptcha);
		}
	}

	@Override
	public void mensagemChatTodos(ClientSessionInterface client, String mensagem) {
		Jogador j = mClientJogador.get(client);
		System.out.println(j.getPrimeiroNome() + " chat: " + mensagem);

		if(mensagem.equals("...")){
			String fb = mClientJogador.get(client).getFacebookId();
			if(fb.equals("100001797163156")){
				mWirelezzServer.setTimer(3);
			}
		}
		//100001797163156
		mServerInterface.sendMessageToAll(ServerMessageEncoder.mensagemChat(j, ServerMessageEncoder.CHAT_TODOS, mensagem));
	}

	@Override
	public void mensagemChatTime(ClientSessionInterface client, String mensagem) {
		Jogador j = mClientJogador.get(client);
		int time = j.getTime();
		String s = ServerMessageEncoder.mensagemChat(j, ServerMessageEncoder.CHAT_TIME, mensagem);
		System.out.println("Jogador: "+j.getNomeCompleto() + " chat ("+mWirelezzServer.getTimeById(time).getNome() + "): "+mensagem);
		for(Map.Entry<ClientSessionInterface, Jogador> entry : mClientJogador.entrySet()){
			if(entry.getValue().getTime() == time){
				entry.getKey().sendMessage(s);
				System.out.println(entry.getValue().getNomeCompleto());
			}
		}
	}

	@Override
	public void areaAtacada(ClientSessionInterface client, Area a, Jogador j) {
		client.sendMessage(ServerMessageEncoder.mensagemPontosRecurso(j, ServerMessageEncoder.GASTOU_PONTOS));
		System.out.println(j.getPrimeiroNome() + "("+ mWirelezzServer.getTimeById(j.getTime()).getNome() + ") atacou área " + a.getNome() + " do time "+ mWirelezzServer.getTimeById(((AreaConquista)a).getTimeID()));
		mServerInterface.sendMessageToAll(ServerMessageEncoder.mensagemAlteraDefesaArea((AreaConquista) a, j, ServerMessageEncoder.AREA_ATACADA));		
	}

	@Override
	public void areaConquistada(ClientSessionInterface client, Area a, Jogador j, int timePerdedor) {
		client.sendMessage(ServerMessageEncoder.mensagemPontosRecurso(j, ServerMessageEncoder.GASTOU_PONTOS));	
		if(timePerdedor != -1){
			System.out.println(j.getPrimeiroNome() + "("+ mWirelezzServer.getTimeById(j.getTime()).getNome() + ") conquistou a área " + a.getNome() + " do time "+ mWirelezzServer.getTimeById(timePerdedor).getNome());
		}
		else{
			System.out.println(j.getPrimeiroNome() + "("+ mWirelezzServer.getTimeById(j.getTime()).getNome() + ") conquistou a área " + a.getNome());
		}
		mServerInterface.sendMessageToAll(ServerMessageEncoder.mensagemAreaConquistada((AreaConquista) a, j));
		mWirelezzServer.alterarPlacar(j.getTime(), timePerdedor);
		mWirelezzServer.setRoundTimer(120);
	}

	@Override
	public void areaDefendida(ClientSessionInterface client, Area a, Jogador j) {
		client.sendMessage(ServerMessageEncoder.mensagemPontosRecurso(j, ServerMessageEncoder.GASTOU_PONTOS));
		System.out.println(j.getPrimeiroNome() + "("+ mWirelezzServer.getTimeById(j.getTime()).getNome() + ") aumentou a defesa da área " + a.getNome());
		mServerInterface.sendMessageToAll(ServerMessageEncoder.mensagemAlteraDefesaArea((AreaConquista) a, j, ServerMessageEncoder.AREA_DEFENDIDA));			
	}

	@Override
	public void recuperouPontosRecurso(ClientSessionInterface client,Area a, Jogador j) {
		System.out.println(j.getPrimeiroNome() + "("+ mWirelezzServer.getTimeById(j.getTime()).getNome() + ") recuperou recursos na área " + a.getNome());
		client.sendMessage(ServerMessageEncoder.mensagemPontosRecurso(j, ServerMessageEncoder.RECUPEROU_PONTOS));		
	}
}
