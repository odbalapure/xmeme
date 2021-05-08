package com.xmeme.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserRepositoryService userRepositoryService;

	@Autowired
	private UserRepositiry userReposiotry;
	
	@PostMapping("/user/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		try {
			User userResponse = userReposiotry.getUserByUsername(user.getUsername());
			try {
				if (userResponse.getUsername().equals(user.getUsername())) {
					return new ResponseEntity<>(user.getUsername() + " username is already taken!",
							HttpStatus.CONFLICT);
				}
			} catch (NullPointerException e) {
				System.out.println("User name is not taken, continuing for registration...");
			}
			
			User _user = new User();
			
			// encrypt the password
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String rawPassword = user.getPassword();
			String encodedPassword = encoder.encode(rawPassword);

			// username password to be saved
			_user.setUsername(user.getUsername());
			_user.setPassword(encodedPassword);

			userRepositoryService.registerUser(_user);

			return new ResponseEntity<>("User registration complete, welcome " + user.getUsername() + "!",
					HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("User couldn't be registered!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user/get")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> userList = userReposiotry.findAll();
	
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	
	
	/*
	 * @PutMapping("/user/activate") public String activateUser(@RequestBody User
	 * user) { User userResponse =
	 * userReposiotry.getUserByUsername(user.getUsername());
	 * userResponse.setUsername(user.getUsername());
	 * userResponse.setPassword(user.getPassword()); userResponse.setEnabled(true);
	 * 
	 * userReposiotry.save(userResponse);
	 * 
	 * return "User activated!"; }
	 */
	
}
