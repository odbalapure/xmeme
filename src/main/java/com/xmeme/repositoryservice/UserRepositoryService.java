package com.xmeme.repositoryservice;

import java.util.List;

import com.xmeme.dto.User;

public interface UserRepositoryService {

	void registerUser(User user);

	User getUser(String userName);

	List<User> getAllUsers();

	void activateUser(User user);

}
