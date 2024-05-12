package com.example.xiao.logmanager.server.log.handler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient(autoRegister = false)
@EnableAspectJAutoProxy
public class LogHandlerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogHandlerApplication.class, args);
    }
}
