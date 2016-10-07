package com.kffb.analyzer.service;

import java.util.HashMap;

import com.kffb.analyzer.dto.User;

public class LoginService {
	
	HashMap <String,String> users = new HashMap();
	
	public LoginService() {
		users.put("johndoe", "John Doe");
		users.put("janedoe", "Jane Doe");
		users.put("jguru", "Java Guru");
	}

	public boolean authenticate(String username, String password) {
		if (password == null || password.trim() == null) {
			return false;
		}
		return true;
	}

	public User getUserDetails (String username) {
		User user = new User();
		user.setUserName(users.get(username));
		user.setUserId(username);
		return user;
	}
}
