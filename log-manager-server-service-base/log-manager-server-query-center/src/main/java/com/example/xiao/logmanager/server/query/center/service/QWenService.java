package com.example.xiao.logmanager.server.query.center.service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QWenService {
    @Value("${alibaba.qwen.api-key}")
    private String API_KEY;
    private final Generation gen = new Generation();

    public String call(String prompt) {
        MessageManager msgManager = new MessageManager(10);
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(prompt).build();
        msgManager.add(userMsg);
        QwenParam param = QwenParam.builder().apiKey(API_KEY).
                model(Generation.Models.QWEN_TURBO)
                .messages(msgManager.get())
                .resultFormat(QwenParam.ResultFormat.MESSAGE)
                .build();
        GenerationResult result = null;
        try {
            log.info("call TongYiQianWen,req={}", prompt);
            result = gen.call(param);
            String content = result.getOutput().getChoices().get(0).getMessage().getContent();
            log.info("call TongyiQianWen,resp={}", content);
            return content;
        } catch (NoApiKeyException e) {
            log.error("未配置通义千问API_KEY");
            throw new RuntimeException(e);
        } catch (InputRequiredException e) {
            log.error("未输入内容", e);
            return null;
        }
    }
}
