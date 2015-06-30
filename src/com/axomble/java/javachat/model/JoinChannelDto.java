package com.axomble.java.javachat.model;

import java.io.Serializable;

public class JoinChannelDto implements Serializable{

	private static final long serialVersionUID = 8498194778856870664L;
	
	private String mUserName;

	public JoinChannelDto(String userName) {
		mUserName = userName;
	}

	public String getUserName() {
		return mUserName;
	}
	

	
}
