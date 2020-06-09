package com.breno.apicastgroup.dto;

public class UserAuthenticateDTO {

	private String email;
	private String type;
	private String token;

	public UserAuthenticateDTO() {
	}

	public UserAuthenticateDTO(String email, String token, String type) {
		this.email = email;
		this.token = token;
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
