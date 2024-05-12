package com.example.xiao.logmanager.server.log.handler.component;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Aspect
@Slf4j
public class EsClientAspect {
    @Around("within(co.elastic.clients.elasticsearch.ElasticsearchClient)")
    public Object surroundWithRetry(ProceedingJoinPoint pjp) throws Throwable {
        Object result;
        //最多执行3次,抛异常时进行重试
        for (int i = 0; i < 3; i++) {
            try {
                result = pjp.proceed();
                return result;
            } catch (IOException e) {
                //连接超时,执行重试,其他异常不重试直接抛
                if (e.getMessage() != null && (e.getMessage().contains("Connection reset") || e.getMessage().contains("timeout"))) {
                    log.error("ESClient Connection Timeout", e);
                    continue;
                }
                throw e;
            }
        }
        //不会执行
        return null;
    }
}
