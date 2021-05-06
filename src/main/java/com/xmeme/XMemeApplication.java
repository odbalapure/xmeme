package com.xmeme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication()
public class XMemeApplication {

	public static void main(String[] args) {
		SpringApplication.run(XMemeApplication.class, args);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String str = "pass";
		String encode = encoder.encode(str);
		
		System.out.println("Encoded Pass: " +encode);
	}

}
