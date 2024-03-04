package com.example.xiao.logmanager.server.common.config;

import com.example.xiao.logmanager.server.common.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime日志格式化配置
 */
@Configuration
public class DateConverterConfig {
    /**
     * 自定义<strong>请求参数</strong>解析器,处理LocalDateTime格式
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if (StringUtils.isBlank(source)) {
                    return null;
                }
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        };
    }

    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                if (StringUtils.isBlank(source)) {
                    return null;
                }
                return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        };
    }


    /**
     * 自定义响应体Json序列化格式
     */
    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        //基于JsonUtil配置的ObjectMapper,调用copy(),如需调整objectMapper配置不会影响到工具类
        ObjectMapper objectMapper = JsonUtil.getObjectMapper().copy();
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }


}
