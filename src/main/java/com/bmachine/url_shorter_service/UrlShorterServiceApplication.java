package com.bmachine.url_shorter_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class UrlShorterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShorterServiceApplication.class, args);
	}

}
