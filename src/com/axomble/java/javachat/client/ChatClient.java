package com.axomble.java.javachat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.axomble.java.javachat.base.Config;
import com.axomble.java.javachat.model.JoinChannelDto;
import com.axomble.java.javachat.model.MessageDto;

public class ChatClient {

	String mUserName = null;
	
	private Socket mConnectionSocket;
	
	private ObjectInputStream mConnectionInputStream;
	private ObjectOutputStream mConnectOutputStream;

	private ServerInputListenerThread mServerInputListenerThread = new ServerInputListenerThread();
	
	public void connect(){
		try {
			System.out.println("Connecting to server...");
			mConnectionSocket = new Socket( Config.SERVER_IP, Config.SERVER_PORT);
			mConnectOutputStream = new ObjectOutputStream(mConnectionSocket.getOutputStream());
			mConnectionInputStream = new ObjectInputStream(mConnectionSocket.getInputStream());
			mServerInputListenerThread.start();
			System.out.println("connected");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void disconnect(){
		try {
			mConnectionSocket.close();
			mConnectionInputStream.close();
			mConnectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void joinChannel( String userName ){
		try {
			mConnectOutputStream.writeObject( new JoinChannelDto(userName) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage( String message ){
		try {
			mConnectOutputStream.writeObject( new MessageDto(message) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleServerInput( Object object ){
		if( object instanceof MessageDto ){
			MessageDto chatMessageDto = (MessageDto) object;
			System.out.println(chatMessageDto.getMessage());
		}
	}
	
	


	private class ServerInputListenerThread extends Thread{
		@Override
		public void run() {
			while( true ){
				try {
					handleServerInput( mConnectionInputStream.readObject());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * TEST ENTRY POINT
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("### Client ###");
		ChatClient chatClient = new ChatClient();
		chatClient.connect();
		if( args.length == 0 ){
			UserInputHandler userInputHandler = new UserInputHandler(chatClient);
		}else{
			ChatBot chatBot = new ChatBot(chatClient);
		}

	}
	
}
