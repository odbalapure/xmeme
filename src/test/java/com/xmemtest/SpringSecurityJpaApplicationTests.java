package com.xmemtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class SpringSecurityJpaApplicationTests {

	@Test
	void contextLoads() {
		
	}
	
	@Test
	void hello() {
		String hello = "hello";
		assertEquals("hello", hello);
	}

}
