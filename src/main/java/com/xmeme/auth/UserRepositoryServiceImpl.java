package com.xmeme.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryServiceImpl implements UserRepositoryService {

	@Autowired
	private UserRepositiry userRepository;
	
	@Override
	public void registerUser(User user) {
		userRepository.save(user);
	}

}
