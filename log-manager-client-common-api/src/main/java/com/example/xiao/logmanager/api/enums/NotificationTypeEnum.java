package com.example.xiao.logmanager.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.example.xiao.logmanager.server.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum NotificationTypeEnum implements BaseEnum {
    STATION_MESSAGE(1, "站内信通知"),
    EMAIL(2, "邮件通知"),
    SHORT_MESSAGE(3, "短信通知");
    @JsonValue
    @EnumValue
    private final Integer code;

    @Getter
    private final String desc;

    @Override
    public Integer getCode() {
        return code;
    }

    public static NotificationTypeEnum of(Integer code) {
        return Arrays.stream(values()).filter(e -> e.code.equals(code)).findFirst().orElse(null);
    }
}
