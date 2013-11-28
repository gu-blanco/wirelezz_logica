package usp.wirelezzgame.server;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.Time;
import usp.wirelezzgame.core.area.Area;
import usp.wirelezzgame.core.area.AreaConquista;
import usp.wirelezzgame.core.captcha.Captcha;

@SuppressWarnings("unchecked")
public class ServerMessageEncoder {

	public static final int RECUPEROU_PONTOS = 0;
	public static final int GASTOU_PONTOS = 1;

	public static final int CHAT_TODOS = 0;
	public static final int CHAT_TIME = 1;

	public static final int AREA_DEFENDIDA = 0;
	public static final int AREA_ATACADA = 1;

	public static String nomeServer(String nomeServidor){
		
		JSONObject data = new JSONObject();
		data.put("nomeServidor", nomeServidor);

		return toJsonMessage(0,data);
	}
	
	public static String timesData(List<Time> times){
		JSONArray timesArray = new JSONArray();
		JSONObject time;
		for(Time t : times){
			time = timeEncode(t);
			timesArray.add(time);
		}
		return toJsonMessage(2,timesArray);
	}
	
	public static String areasData(List<Area> areas){
		JSONArray areasArray = new JSONArray();
		JSONObject area;
		for(Area a : areas){
			area = areaEncode(a);
			areasArray.add(area);
		}
		return toJsonMessage(3,areasArray);
	}
	
	public static String jogadorIdTime(Jogador j){
		JSONObject jog = new JSONObject();
		jog.put("idJogador",new Integer(j.getID()));
		jog.put("idTime",new Integer(j.getTime()));
		

		return toJsonMessage(5,jog);
	}
	
	public static String novoJogador(Jogador j){
		JSONObject jog = jogadorEncode(j); // pega outros dados deste metodo
		jog.put("idTime", new Integer(j.getTime()));	// fica faltando o time
		return toJsonMessage(6,jog);
	}
	
	public static String mensagemCaptcha(Captcha c){
		JSONObject obj = new JSONObject();
		obj.put("idCaptcha", new Integer(c.getID()));	
		obj.put("imagem", c.getImage());		
		return toJsonMessage(10,obj);
	}
	
	public static String mensagemResultadoCaptcha(boolean b){
		JSONObject obj = new JSONObject();
		obj.put("resultado",new Boolean(b));		
		return toJsonMessage(11,obj);
	}
	
	public static String mensagemPontosRecurso(Jogador j, int modo){
		JSONObject obj = new JSONObject();
		obj.put("pontos",new Integer(j.getPontosRecurso()));
		obj.put("modo",new Integer(modo));		
		return toJsonMessage(12,obj);
	}
	
	public static String mensagemAlteraDefesaArea(AreaConquista a, Jogador j, int acao){
		JSONObject obj = new JSONObject();
		obj.put("idArea",new Integer(a.getID()));
		obj.put("defesa",new Integer(a.getNivelDefesa()));		
		obj.put("acao",new Integer(acao));		
		obj.put("idJogador",new Integer(j.getID()));		
		return toJsonMessage(13,obj);
	}
	
	public static String mensagemAreaConquistada(AreaConquista a, Jogador j){
		JSONObject obj = new JSONObject();
		obj.put("idArea",new Integer(a.getID()));
		obj.put("defesa",new Integer(a.getNivelDefesa()));		
		obj.put("idTime",new Integer(a.getTimeID()));		
		obj.put("idJogador",new Integer(j.getID()));		
		return toJsonMessage(14,obj);
	}
	
	public static String mensagemVitoriaTime(Time t){
		JSONObject obj = new JSONObject();		
		obj.put("idTime",new Integer(t.getID()));		
		return toJsonMessage(15,obj);
	}
	
	public static String mensagemJogadorDesconectou(Jogador j){
		JSONObject obj = new JSONObject();		
		obj.put("idJogador",new Integer(j.getID()));		
		return toJsonMessage(16,obj);
	}
	
	public static String mensagemChat(Jogador j, int tipo, String mensagem){
		JSONObject obj = new JSONObject();		
		obj.put("idJogador",new Integer(j.getID()));	
		obj.put("tipo",new Integer(tipo));	
		obj.put("mensagem",mensagem);		
		return toJsonMessage(19,obj);
	}
	
	private static String toJsonMessage(int code, JSONAware data){
		JSONObject obj = new JSONObject();
		obj.put("code",new Integer(code));
		obj.put("data",data);
		return obj.toJSONString();
	}
	
	private static JSONObject jogadorEncode(Jogador j){
		JSONObject obj = new JSONObject();
		obj.put("idJogador", new Integer(j.getID()));
		obj.put("nomeJogador", j.getPrimeiroNome());
		obj.put("nomeCompleto", j.getNomeCompleto());
		obj.put("facebookID", j.getFacebookId());
		return obj;
	}
	
	private static JSONObject timeEncode(Time t){
		JSONObject obj = new JSONObject();
		obj.put("idTime", new Integer(t.getID()));
		obj.put("nomeTime", t.getNome());
		obj.put("cor", new Integer(t.getCor().ordinal()));
		
		JSONArray jogadoresArray = new JSONArray();
		JSONObject jogador;

		List<Jogador> js = t.getJogadores();
		for(Jogador j : js){
			jogador = jogadorEncode(j);
			jogadoresArray.add(jogador);
		}
		
		obj.put("jogadores",jogadoresArray);
		return obj;
	}
	
	private static JSONObject areaEncode(Area a){
		JSONObject obj = new JSONObject();
		obj.put("idArea", new Integer(a.getID()));
		obj.put("latitude", new Double(a.getLatitude()));
		obj.put("longitude", new Double(a.getLongitude()));
		obj.put("raio", new Double(a.getRaio()));
		
		if(a instanceof AreaConquista){
			AreaConquista ac = (AreaConquista) a;
			obj.put("tipo", AreaConquista.TIPO);
			obj.put("idTime", new Integer(ac.getTimeID()));
			obj.put("defesa", new Integer(ac.getNivelDefesa()));
		}		
		return obj;
	}

}
