package com.example.xiao.logmanager.server.alert.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.example.xiao.logmanager.server.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum AlertLevelEnum implements BaseEnum {
    NOTIFY(1, "提示"),
    SECONDARY(2, "次要"),
    IMPORTANT(3, "重要"),
    EMERGENCY(4, "紧急"),
    ;
    @JsonValue
    @EnumValue
    private final Integer code;
    private final String desc;

    @Override
    public Integer getCode() {
        return code;
    }

    public AlertLevelEnum of(Integer code) {
        return Arrays.stream(values()).filter(e -> e.code.equals(code)).findFirst().orElse(null);
    }
}
