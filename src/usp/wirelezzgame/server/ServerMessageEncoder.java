package usp.wirelezzgame.server;

import java.util.List;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.Partida;
import usp.wirelezzgame.core.Time;
import usp.wirelezzgame.core.area.Area;
import usp.wirelezzgame.core.area.AreaConquista;

@SuppressWarnings("unchecked")
public class ServerMessageEncoder {

	public static String nomeServer(String nomeServidor){
		int MessageCode = 0;
		
		JSONObject data = new JSONObject();
		data.put("nomeServidor", nomeServidor);
		
		JSONObject obj = new JSONObject();
		obj.put("data",data);
		obj.put("code",new Integer(MessageCode));
		return obj.toJSONString();
	}
	
	public static String timesData(List<Time> times){
		int MessageCode = 2;

		JSONArray timesArray = new JSONArray();
		JSONObject time;
		for(Time t : times){
			time = timeEncode(t);
			timesArray.add(time);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("data",timesArray);
		obj.put("code",new Integer(MessageCode));
		return obj.toJSONString();
	}
	
	public static String areasData(List<Area> areas){
		int MessageCode = 3;

		JSONArray areasArray = new JSONArray();
		JSONObject area;
		for(Area a : areas){
			area = areaEncode(a);
			areasArray.add(area);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("data",areasArray);
		obj.put("code",new Integer(MessageCode));
		return obj.toJSONString();
	}
	
	public static String jogadorIdTime(Jogador j){
		int MessageCode = 5;
		JSONObject jog = new JSONObject();
		jog.put("idJogador",new Integer(j.getID()));
		jog.put("idTime",new Integer(j.getTime()));
		
		
		JSONObject obj = new JSONObject();
		obj.put("data",jog);
		obj.put("code",new Integer(MessageCode));
		return obj.toJSONString();
	}
	
	public static String novoJogador(Jogador j){
		int MessageCode = 6;
		
		JSONObject jog = jogadorEncode(j);
		jog.put("idTime", j.getTime());		
		
		JSONObject obj = new JSONObject();
		obj.put("data",jog);
		obj.put("code",new Integer(MessageCode));
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
