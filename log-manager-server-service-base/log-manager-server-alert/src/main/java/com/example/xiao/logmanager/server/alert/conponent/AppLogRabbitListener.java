package com.example.xiao.logmanager.server.alert.conponent;

import com.example.xiao.logmanager.server.alert.config.RabbitConfig;
import com.example.xiao.logmanager.server.alert.entity.es.AppLogEsDocument;
import com.example.xiao.logmanager.server.alert.service.biz.LogAnalyzeService;
import com.example.xiao.logmanager.server.common.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 监听应用日志,消息队列,扫描日志决定是否告警
 */
@RabbitListener(queues = RabbitConfig.APP_LOG_QUEUE)
@Component
@Slf4j
@RequiredArgsConstructor
public class AppLogRabbitListener {
    private static final ObjectMapper objectMapper;
    private final LogAnalyzeService logAnalyzeService;

    static {
        JsonMapper.Builder builder = JsonMapper.builder();
        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化
        builder.serializationInclusion(JsonInclude.Include.ALWAYS);

        //有未知属性 要不要抛异常
        builder.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //是否允许JSON字符串包含未转义的控制字符(值小于32的ASCII字符，包括制表符和换行符)的特性。如果feature设置为false，则在遇到这样的字符时抛出异常。
        builder.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS, true);

        objectMapper = builder.build();

        //处理 时间格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        objectMapper.registerModule(javaTimeModule);

        //驼峰转下划线
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE);
    }

    @RabbitHandler
    public void process(Object messageObj) {
        if (messageObj instanceof Message message) {
            AppLogEsDocument appLog = null;
            try {
                appLog = objectMapper.readValue(message.getBody(), AppLogEsDocument.class);
            } catch (IOException e) {
                log.error("read message {} fail", appLog, e);
                return;
            }
            log.info("received appLog:{}", JsonUtil.toJson(appLog));
            //最多重试2次
            for (int i = 0; i < 3; i++) {
                try {
                    logAnalyzeService.analyze(appLog);
                    return;
                } catch (Exception e) {
                    log.error("consumed appLog {} fail", appLog, e);
                }
            }
            log.error("appLog {} exceeded max retry count", appLog);
        }
    }

}
