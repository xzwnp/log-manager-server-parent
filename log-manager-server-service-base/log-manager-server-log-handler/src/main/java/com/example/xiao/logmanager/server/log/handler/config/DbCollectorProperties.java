package com.example.xiao.logmanager.server.log.handler.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "log-manager.db-collector")
@Component
@Data
public class DbCollectorProperties {
    /**
     * canal server配置
     */
    private String host = "127.0.0.1";
    private Integer port = 11111;
    private String destination = "example";
    private String username = "";
    private String password = "";
    /**
     * 每次最多消费多少条记录
     */
    private Integer maxBatchSize = 1000;
    private List<DataBaseConfig> routers;

    @Data
    public static class DataBaseConfig {
        private String database;
        private String appName;
        private String group;
    }
}
