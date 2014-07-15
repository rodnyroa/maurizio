package com.maurizio.cice.model;

import java.io.Serializable;

public class Pin implements Serializable {
	private String Pin, date, id;
	private User User;

	public Pin() {
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public String getPin() {
		return Pin;
	}

	public void setPin(String pin) {
		Pin = pin;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
