package com.axomble.java.javachat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.axomble.java.javachat.model.JoinChannelDto;
import com.axomble.java.javachat.model.MessageDto;

public class ClientHandler {
	
	private static final String TAG = "CLIENT_HANDLER";
	
	private ChannelHandler mChatChannel;
	
	private JoinChannelDto mJoinChannelDto = null;
	
	private Socket mClientSocket;
	private ObjectInputStream clientInputStream;
	private ObjectOutputStream clientOutputStream;
	private ClientInputListenerThread mClientInputListenerThread = new ClientInputListenerThread();
	


	public ClientHandler(ChannelHandler chatChannel, Socket clientSocket) {
		try {
			mChatChannel = chatChannel;
			mClientSocket = clientSocket;
			clientOutputStream = new ObjectOutputStream(mClientSocket.getOutputStream());
			clientInputStream = new ObjectInputStream(mClientSocket.getInputStream());
			mClientInputListenerThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage( String message ){
		try {
			clientOutputStream.writeObject( new MessageDto(message) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleClientMessage( Object object ){
		if(object instanceof JoinChannelDto){
			System.out.println(TAG+":"+hashCode()+"> JoinChannelDto arrived");
			mJoinChannelDto = (JoinChannelDto) object;
			mChatChannel.joinChannel(mJoinChannelDto.getUserName(), this);
		}else if( object instanceof MessageDto ){
			System.out.println(TAG+":"+hashCode()+"> MessageDto arrived");
			if( mJoinChannelDto != null ){
				MessageDto chatMessageDto = (MessageDto) object;
				mChatChannel.sendPublicMessage(mJoinChannelDto.getUserName(), chatMessageDto.getMessage());
			}else{
				sendMessage( "error> use -join {userName}" );
			}
		}else{
			//TODO unknown
		}
	}
	
	
	
	private class ClientInputListenerThread extends Thread{
		@Override
		public void run() {
			while( true ){
				try {
					handleClientMessage( clientInputStream.readObject() );
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
