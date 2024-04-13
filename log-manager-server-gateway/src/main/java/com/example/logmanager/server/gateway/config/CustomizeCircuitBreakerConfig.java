package com.example.logmanager.server.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CustomizeCircuitBreakerConfig {

    @Bean
    public ReactiveResilience4JCircuitBreakerFactory defaultCustomizer(CircuitBreakerRegistry circuitBreakerRegistry, TimeLimiterRegistry timeLimiterRegistry) {

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom() //
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED) // 滑动窗口的类型为时间窗口
                .slidingWindowSize(10) // 时间窗口的大小为10秒
                .minimumNumberOfCalls(5) // 在单位时间窗口内最少需要5次调用才能开始进行统计计算
                .failureRateThreshold(30) // 在单位时间窗口内调用失败率达到30%后会启动断路器
                .enableAutomaticTransitionFromOpenToHalfOpen() // 允许断路器自动由打开状态转换为半开状态
                .permittedNumberOfCallsInHalfOpenState(5) // 在半开状态下允许进行正常调用的次数
                .waitDurationInOpenState(Duration.ofSeconds(5)) // 断路器打开状态转换为半开状态需要等待60秒
                .recordExceptions(Throwable.class) // 所有异常都当作失败来处理
                .build();
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(30)) //超时时间30s
                .build();
        ReactiveResilience4JCircuitBreakerFactory factory = new ReactiveResilience4JCircuitBreakerFactory(circuitBreakerRegistry, timeLimiterRegistry);
        factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig).build());

        return factory;
    }
}
