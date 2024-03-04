package com.example.xiao.logmanager.server.collect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DbCollectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbCollectorApplication.class, args);
    }
}
