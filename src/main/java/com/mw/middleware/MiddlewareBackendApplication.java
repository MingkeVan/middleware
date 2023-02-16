package com.mw.middleware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mw.middleware.mapper")
public class MiddlewareBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiddlewareBackendApplication.class, args);
	}

}
