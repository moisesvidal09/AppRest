package com.company.apprest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//@SpringBootApplication
@EnableCaching

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})

public class AppRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppRestApplication.class, args);
	}

}
