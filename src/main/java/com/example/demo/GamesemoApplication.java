package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.example.demo.mapper")
public class GamesemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamesemoApplication.class, args);
	}

}
