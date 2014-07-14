package com.maurizio.cice.model;

import java.io.Serializable;

public class Response implements Serializable{

	private String Token, Response,MessageCode,UserId;
	private User User;

	public String getToken() {
		return Token;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getMessageCode() {
		return MessageCode;
	}

	public void setMessageCode(String messageCode) {
		MessageCode = messageCode;
	}

	public void setToken(String token) {
		Token = token;
	}

	public String getResponse() {
		return Response;
	}

	public void setResponse(String response) {
		Response = response;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public Response() {
		// TODO Auto-generated constructor stub
	}

}
