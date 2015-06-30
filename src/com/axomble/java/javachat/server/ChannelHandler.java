package com.axomble.java.javachat.server;

import java.util.HashMap;
import java.util.Map;

public class ChannelHandler {
	
	private static final String TAG = "CHANNEL_HANDLER";
	
	private static final String CHANNEL_NAME = "CHANNEL";
	
	private Map<String,ClientHandler> mChannelUserMap = new HashMap<>();
	
	public void joinChannel( String userName, ClientHandler clientHandler ){
		mChannelUserMap.put(userName, clientHandler);
		clientHandler.sendMessage(CHANNEL_NAME+"> Hello "+userName);
		sendPublicMessage(CHANNEL_NAME, "\""+userName+"\" join channel" );
	}
	
	public void sendPublicMessage( String userName, String message ){
		System.out.println(TAG+"> userName="+userName+", message="+message);
		for( ClientHandler clientHandler : mChannelUserMap.values() ){
			clientHandler.sendMessage(userName+"> "+message);
		}
	}
	
	public boolean sendPrivateMessage(String fromUserName, String toUserName, String message){
		ClientHandler toClientHandler = mChannelUserMap.get(toUserName);
		if(toClientHandler != null){
			toClientHandler.sendMessage( fromUserName + "(private): " + message );
			return true;
		}else{
			return false;
		}
	}
	
	public void leafChannel( String userName ){
		//TODO
	}
	
}
