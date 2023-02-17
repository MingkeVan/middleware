package com.mw.middleware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.mw.middleware.mapper")
@EnableScheduling
public class MiddlewareBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiddlewareBackendApplication.class, args);
	}

}
