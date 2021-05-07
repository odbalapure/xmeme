package com.xmeme.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class ManageUsersController {

	@Autowired
	private UserRepositoryService managerUserRepositoryService;
	
	@GetMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		try {
			User _user = new User();
			_user.setUsername(user.getUsername());
			_user.setPassword(user.getPassword());
			
			managerUserRepositoryService.registerUser(_user);
			
			return new ResponseEntity<>("User registration complete!", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("User couldn't be registered!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
