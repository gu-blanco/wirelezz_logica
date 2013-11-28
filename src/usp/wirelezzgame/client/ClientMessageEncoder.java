package usp.wirelezzgame.client;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import usp.wirelezzgame.core.area.Area;

@SuppressWarnings("unchecked")
public class ClientMessageEncoder {

	public static final int DEFENDER_AREA = 0;
	public static final int ATACAR_AREA = 1;
	public static final int RECUPERAR_PONTOS_AREA = 2;

	public static String dadosJogador(String primeiroNome,String nomeCompleto, String facebookID){
		
		JSONObject data = new JSONObject();
		data.put("nomeJogador", primeiroNome);
		data.put("nomeCompleto", nomeCompleto);
		data.put("facebookID", facebookID);

		return toJsonMessage(1,data);
	}
	
	public static String timeJogador(int id){
		
		JSONObject data = new JSONObject();
		data.put("idTime", new Integer(id));

		return toJsonMessage(4,data);
	}
	
	public static String interagirArea(Area a, double latitude, double longitude, int acao){
		
		JSONObject data = new JSONObject();
		data.put("idArea", new Integer(a.getID()));
		data.put("latitude", new Double(latitude));
		data.put("longitude", new Double(longitude));
		data.put("acao", new Integer(acao));

		return toJsonMessage(7,data);
	}
	
	public static String responderCaptcha(int idCaptcha, String resposta){
		
		JSONObject data = new JSONObject();
		data.put("idCaptcha", new Integer(idCaptcha));
		data.put("resposta", resposta);

		return toJsonMessage(9,data);
	}
	
	public static String mensagemChatTodos(String mensagem){
		
		JSONObject data = new JSONObject();
		data.put("mensagem", mensagem);

		return toJsonMessage(17,data);
	}
	
	public static String mensagemChatTime(String mensagem){
		
		JSONObject data = new JSONObject();
		data.put("mensagem", mensagem);

		return toJsonMessage(18,data);
	}
	
	
	
	
	
	private static String toJsonMessage(int code, JSONAware data){
		JSONObject obj = new JSONObject();
		obj.put("code",new Integer(code));
		obj.put("data",data);
		return obj.toJSONString();
	}
}
