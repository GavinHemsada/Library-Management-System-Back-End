package com.example.ngBACKEND;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NgBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NgBackendApplication.class, args);
	}

}
