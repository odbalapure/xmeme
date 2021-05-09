package com.xmeme.repositoryservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xmeme.dto.User;
import com.xmeme.repository.UserRepository;

@Repository
public class UserRepositoryServiceImpl implements UserRepositoryService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void registerUser(User user) {
		userRepository.save(user);
	}

	@Override
	public User getUser(String userName) {
		return userRepository.getUserByUsername(userName);
	}

	@Override
	public void activateUser(User user) {
		userRepository.save(user);
	}

}
