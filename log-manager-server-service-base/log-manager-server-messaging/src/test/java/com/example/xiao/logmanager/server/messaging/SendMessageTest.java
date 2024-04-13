package com.example.xiao.logmanager.server.messaging;

import com.example.xiao.logmanager.server.messaging.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class SendMessageTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void sendSimpleMail() {
        emailService.sendSimpleMail("2633247964@qq.com","测试邮件","简单测试一下");
    }
}
