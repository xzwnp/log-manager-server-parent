package com.example.xiao.logmanager.server.query.center.enums;

import com.example.xiao.logmanager.server.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LogLevelEnum implements BaseEnum {
    TRACE(1, "TRACE"),
    DEBUG(2, "DEBUG"),
    INFO(3, "INFO"),
    WARN(4, "WARN"),
    ERROR(5, "ERROR"),
    FATAL(6, "FATAL");

    @JsonValue
    private final int code;

    private final String name;

    //code转枚举类
    public static LogLevelEnum of(int code) {
        for (LogLevelEnum e : LogLevelEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
