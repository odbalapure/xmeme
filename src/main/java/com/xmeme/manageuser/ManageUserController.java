package com.xmeme.manageuser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManageUserController {

	@GetMapping("/users")
	public String getAllUsers() {
		return "List of Users";
	}
	
	
}
