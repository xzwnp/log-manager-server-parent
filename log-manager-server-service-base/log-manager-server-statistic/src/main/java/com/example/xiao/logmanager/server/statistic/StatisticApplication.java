package com.example.xiao.logmanager.server.statistic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.xiao.logmanager")
@MapperScan(basePackages = "com.example.xiao.logmanager.server.statistic.dao")
public class StatisticApplication {
	public static void main(String[] args) {
		SpringApplication.run(StatisticApplication.class, args);
	}
}
