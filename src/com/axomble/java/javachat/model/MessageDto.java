package com.axomble.java.javachat.model;

import java.io.Serializable;

public class MessageDto  implements Serializable{

	private static final long serialVersionUID = 8498194778856870664L;
	

	
	private String mMessage;

	public MessageDto( String message) {
		mMessage = message;
	}


	public String getMessage() {
		return mMessage;
	}
	
}
