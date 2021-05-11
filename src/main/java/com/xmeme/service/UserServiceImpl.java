package com.xmeme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmeme.dto.User;
import com.xmeme.repositoryservice.UserRepositoryService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepositoryService userRepositoryService;

	@Override
	public List<User> getAllUsers() {
		return userRepositoryService.getAllUsers();
	}

	@Override
	public void registerUser(User user) {
		userRepositoryService.registerUser(user);
	}

	@Override
	public User getUser(String userName) {
		return userRepositoryService.getUser(userName);
	}

	@Override
	public void activateUser(User user) {
		userRepositoryService.activateUser(user);
	}

}
