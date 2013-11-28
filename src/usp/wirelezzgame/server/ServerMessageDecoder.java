package usp.wirelezzgame.server;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import usp.wirelezzgame.client.ClientMessagesCallback;

public class ServerMessageDecoder {
	
	private ServerMessageCallback mCallback;
	
	public ServerMessageDecoder(ServerMessageCallback callback){
		mCallback = callback;
	}
	
	public void parse(String message){
		Object obj=JSONValue.parse(message);
		JSONObject json = (JSONObject) obj;
		long code = (long) json.get("code");
		JSONAware data = (JSONAware) json.get("data");
		parseMessage(new Long(code).intValue(), data);
	}
	
	private void parseMessage(int code, JSONAware data){
		switch(code){
			case 0:
				
			break;
		}
	}
	
}
