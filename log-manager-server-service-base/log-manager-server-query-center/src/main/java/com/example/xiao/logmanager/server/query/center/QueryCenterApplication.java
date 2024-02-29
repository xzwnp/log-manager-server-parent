package com.example.xiao.logmanager.server.query.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.xiao.logmanager")
public class QueryCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueryCenterApplication.class, args);
	}

}
