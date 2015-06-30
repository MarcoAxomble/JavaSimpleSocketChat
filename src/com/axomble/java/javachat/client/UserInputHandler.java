package com.axomble.java.javachat.client;

import java.io.IOException;
import java.util.Scanner;

import com.axomble.java.javachat.base.Config;

public class UserInputHandler {

	private ChatClient mChatClient;
	
	private UserInputListenerThread mUserInputListenerThread = new UserInputListenerThread();
	
	private Scanner mUserInputStream;
	
	public UserInputHandler( ChatClient chatClient ){
		mChatClient = chatClient;
		mUserInputStream = new Scanner(System.in);
		mUserInputListenerThread.start();		
	}
	
	private void handleUserInput( String inputString ){
			if( inputString.startsWith( Config.COMMAND_JOIN ) ){
				mChatClient.joinChannel(inputString.replace(Config.COMMAND_JOIN, "").trim());
			}else{
				mChatClient.sendMessage(inputString);
			}
	}
	
	private class UserInputListenerThread extends Thread{
		@Override
		public void run() {
			while( true ){
				String inputString = mUserInputStream.nextLine();
				handleUserInput(inputString);
			}
		}
	}
}
