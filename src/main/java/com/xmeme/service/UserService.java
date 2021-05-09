package com.xmeme.service;

import java.util.List;

import com.xmeme.dto.User;

public interface UserService {

	void registerUser(User user);

	User getUser(String userName);

	List<User> getAllUsers();

	void activateUser(User user);

}
