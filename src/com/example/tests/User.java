package com.example.tests;

public class User {

	private String login;
	private String password;
	private String email;
	private String name;
	
	public User() {		
	}

	public User(String login, String password, String email) {
		this.login = login;
		this.password = password;
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public User setLogin(String login) {
		this.login = login;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

}
