package com.maurizio.cice.model;

import java.io.Serializable;

public class User implements Serializable{
	private String FullName, UserName, Email, UserId;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

}
