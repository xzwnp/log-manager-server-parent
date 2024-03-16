package com.example.xiao.logmanager.server.user.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/sys/test")
@Slf4j
public class AlertTestController {
    Random random = new Random();

    @GetMapping("alertTest")
    public void testAlert() {
        int i = random.nextInt(100);
        if (i < 10) {
            throw new RuntimeException(String.format("hhhh %d test", i));
        }
        if (i > 90) {
            throw new RuntimeException("connection timeout");
        }
        log.info("do connection test");
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
