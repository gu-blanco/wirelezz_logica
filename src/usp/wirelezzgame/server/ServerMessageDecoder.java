package usp.wirelezzgame.server;

import usp.wirelezzgame.client.ClientMessagesCallback;

public class ServerMessageDecoder {
	
	private ServerMessageCallback mCallback;
	
	public ServerMessageDecoder(ServerMessageCallback callback){
		mCallback = callback;
	}
	
	public void parse(String message){
		
	}
	
}
