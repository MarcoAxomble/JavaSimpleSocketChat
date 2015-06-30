package com.axomble.java.javachat.client;

import java.io.IOException;
import java.util.Scanner;

import com.axomble.java.javachat.base.Config;
import com.axomble.java.javachat.base.RandomHelper;

public class ChatBot {

	private ChatClient mChatClient;

	private BotThread mBotThread = new BotThread();


	public ChatBot( ChatClient chatClient ){
		mChatClient = chatClient;
		mChatClient.joinChannel("Bot"+RandomHelper.nextInt( 10, 999 ));
		mBotThread.start();		
	}

	private void doSomeRandomAction(  ){
		mChatClient.sendMessage(RandomHelper.nextMessage());
	}

	private class BotThread extends Thread{
		@Override
		public void run() {
			while( true ){
				try {
					Thread.sleep(RandomHelper.nextLong(1000, 10000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				doSomeRandomAction();
			}
		}
	}
}

