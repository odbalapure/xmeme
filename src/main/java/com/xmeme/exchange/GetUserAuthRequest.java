package com.xmeme.exchange;

import com.sun.istack.NotNull;

public class GetUserAuthRequest {

	@NotNull
	private String userName;

	@NotNull
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
