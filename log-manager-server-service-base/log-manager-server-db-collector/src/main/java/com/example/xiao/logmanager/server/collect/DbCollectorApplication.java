package com.example.xiao.logmanager.server.collect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient(autoRegister = false)
@EnableAspectJAutoProxy
public class DbCollectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbCollectorApplication.class, args);
    }
}
