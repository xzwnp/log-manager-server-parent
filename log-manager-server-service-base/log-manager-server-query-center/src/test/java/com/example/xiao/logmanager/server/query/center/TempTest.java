package com.example.xiao.logmanager.server.query.center;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TempTest {
    @Test
    public void test(){
        var time = LocalDateTime.parse("2024-02-10 23:28:50.500", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println(time);
    }
}
