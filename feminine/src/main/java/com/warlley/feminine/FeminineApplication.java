package com.warlley.feminine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FeminineApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeminineApplication.class, args);
	}

}
