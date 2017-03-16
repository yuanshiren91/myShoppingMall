package com.netease.myShoppingMall.base.domain;

public class User {
	private String username;
	private String password;
	private String userrole;
	public User() {
		
	}
	public User(String username, String password, String userrole) {
		super();
		this.username = username;
		this.password = password;
		this.userrole = userrole;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserrole() {
		return userrole;
	}

	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}
	
}
