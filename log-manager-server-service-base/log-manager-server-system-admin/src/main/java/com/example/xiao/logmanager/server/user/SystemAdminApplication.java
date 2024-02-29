package com.example.xiao.logmanager.server.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.xiao.logmanager")
public class SystemAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemAdminApplication.class, args);
	}

}
