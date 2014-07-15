package com.maurizio.cice.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable{

	private String Token, Response,MessageCode,UserId;
	private User User;
	private Pin Pin;
	private ArrayList<Pin> Pins;
	

	public Pin getPin() {
		return Pin;
	}

	public void setPin(Pin pin) {
		Pin = pin;
	}

	public ArrayList<Pin> getPins() {
		return Pins;
	}

	public void setPins(ArrayList<Pin> pins) {
		Pins = pins;
	}

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
