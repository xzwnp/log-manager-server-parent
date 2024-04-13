package com.example.xiao.logmanager.server.statistic.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;


@Component
@Slf4j
public class EsClientHelper {
    private static ElasticsearchClient esClient;


    public EsClientHelper(ElasticsearchClient esClient) {
        EsClientHelper.esClient = esClient;
    }

    /**
     * 默认最多执行3次(重试2次)
     *
     * @param function
     * @param <R>
     * @return
     */
    public static <R> R executeWithRetry(FunctionWithException<ElasticsearchClient, R> function) {
        return executeWithRetry(function, 2);
    }

    public static <R> R executeWithRetry(FunctionWithException<ElasticsearchClient, R> function, int retryCount) {
        for (int i = 0; i <= retryCount; i++) {
            try {
                return function.apply(esClient);
            } catch (Exception e) {
                //连接超时,执行重试,其他异常不重试
                if (e instanceof SocketTimeoutException) {
                    log.error("ESClient Connection Timeout, {}", e.getMessage());
                    continue;
                }
                throw new RuntimeException("EsClientHelper call esClient fail", e);
            }
        }
        //不会执行
        return null;
    }

    @FunctionalInterface
    public interface FunctionWithException<T, R> {
        R apply(T t) throws Exception;
    }
}
