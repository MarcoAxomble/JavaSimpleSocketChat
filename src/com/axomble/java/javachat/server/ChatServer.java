package com.axomble.java.javachat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.axomble.java.javachat.base.Config;

public class ChatServer {

	private static final String TAG = "SERVER";
	
	private ServerSocket mServerSocket;
	private ClientConnectionListenerThread mClientConnectionListenerThread = new ClientConnectionListenerThread();
	private List<ClientHandler> mCLientHandlerList = new ArrayList<>(); 
	private ChannelHandler mChatChannel = new ChannelHandler();
		
	
	public ChatServer(){
		System.out.println(TAG+"> start");
		try {
			mServerSocket = new ServerSocket( Config.SERVER_PORT );
			mClientConnectionListenerThread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class ClientConnectionListenerThread extends Thread{
		@Override
		public void run() {
			while( true ){
				try {
					System.out.println(TAG+"> wait for clients...");
					Socket newClientSocket = mServerSocket.accept();
					mCLientHandlerList.add(new ClientHandler(mChatChannel, newClientSocket));
					System.out.println(TAG+"> client connected");
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
		System.out.println("### Server ###");
		ChatServer chatServer = new ChatServer();
	}

}
