package com.watertank.test.entity;

/**
 * A pojo entity class for login Credentials for Rest Api /login call
 * @author The Hoff
 *
 */
public class Credentials {

	public String email;
	public String password;
	
	
	public Credentials() {
		// TODO Auto-generated constructor stub
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
