package com.example.xiao.logmanager.server.alert.config;

import com.example.xiao.logmanager.server.common.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String APP_LOG_QUEUE = "applog-queue";

//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter jsonMessageConverter() {
//        ObjectMapper objectMapper = JsonUtil.getObjectMapper().copy();
//        return new Jackson2JsonMessageConverter(objectMapper);
//    }

    @Bean
    public Queue Queue() {
        return new Queue(APP_LOG_QUEUE);
    }

}