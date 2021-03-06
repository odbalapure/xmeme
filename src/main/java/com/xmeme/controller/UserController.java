package com.xmeme.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xmeme.dto.User;
import com.xmeme.exchange.GetUserAuthRequest;
import com.xmeme.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

	private final static Logger logger = Logger.getLogger(UserController.class);
	
	private static final String USER_API = "/user";
	private static final String REGISTER_USER = "/register";
	private static final String GET_ALL_USER = "/get";
	private static final String ACTIVATE_USER = "/activate";
	
	@Autowired
	private UserService userService;
	
	@PostMapping(USER_API + REGISTER_USER)
	@ApiOperation(value = "Method to register a user", notes = "Method is accessible to all type of users", response = String.class)
	public ResponseEntity<String> registerUser(@Valid @RequestBody GetUserAuthRequest user) {
		try {
			User userResponse = userService.getUser(user.getUserName());
			
			try {
				if (userResponse.getUsername().equals(user.getUserName())) {
					return new ResponseEntity<>(user.getUserName() + " username is already taken!",
							HttpStatus.CONFLICT);
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
				logger.error("Something went wrong while registering a user...");
			}

			User _user = new User();

			// encrypt the password
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String rawPassword = user.getPassword();
			String encodedPassword = encoder.encode(rawPassword);

			// username password to be saved
			_user.setUsername(user.getUserName());
			_user.setPassword(encodedPassword);

			logger.info("Registring the following user: " +_user);
			userService.registerUser(_user);
			return new ResponseEntity<>("User registration complete, welcome " + user.getUserName() + "!",
					HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Something went wrong while registring this user...");
			return new ResponseEntity<>("User couldn't be registered!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(USER_API + GET_ALL_USER)
	@ApiOperation(value = "Fetch list of all users", notes = "Admin rights required to perform this operation", response = List.class)
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> userList = userService.getAllUsers();

		try {
			if (userList == null || userList.size() == 0) {
				return new ResponseEntity<>(new ArrayList<User>(), HttpStatus.NOT_FOUND);
			}
		} catch (NullPointerException e) {
			logger.warn("No user present in the DB!");
		}
		
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@PutMapping(USER_API + ACTIVATE_USER +  "/{userName}")
	@ApiOperation(value = "Activate a user for which userName is mandatory is an input", notes = "Admin rights required to perform this operation", response = String.class)
	public ResponseEntity<String> activateUser(@PathVariable String userName) {
		User user = userService.getUser(userName);
		
		try {
			if (user.getUsername() == null) {
				System.out.println("User does not exist!");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.warn("User does not exist, cannot be activated!");
			return new ResponseEntity<>("User does not exist, cannot be activated!", HttpStatus.NOT_FOUND);
		}
		
		if (user.isEnabled()) {
			logger.info("User already activated!");
			return new ResponseEntity<>("User already activated!", HttpStatus.CONFLICT);
		}
		
		// enable the user
		user.setEnabled(true);
		// update the user record
		userService.activateUser(user);
		
		logger.info("Activating the following user: " +user);
		return new ResponseEntity<>("User activated!", HttpStatus.OK);
	}
	

}
