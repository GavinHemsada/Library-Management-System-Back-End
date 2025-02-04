package com.example.ngbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class NgbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NgbackendApplication.class, args);
	}

}
