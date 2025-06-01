package com.AnimeApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AnimeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeAppApplication.class, args);
	}

}
