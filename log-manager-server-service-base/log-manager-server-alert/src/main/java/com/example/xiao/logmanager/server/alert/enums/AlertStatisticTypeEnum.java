package com.example.xiao.logmanager.server.alert.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.example.xiao.logmanager.server.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AlertStatisticTypeEnum implements BaseEnum {
    TRIGGER_IMMEDIATELY(1, "立即触发"),
    FIXED_WINDOW_COUNT(2, "固定窗口计数"),
    SLIDING_WINDOW_COUNT(3, "滑动窗口计数"),
    FIXED_WINDOW_PERCENT(4, "固定窗口百分比"),
    ;
    @JsonValue
    @EnumValue
    private final Integer code;
    private final String desc;

    @Override
    public Integer getCode() {
        return code;
    }

    public AlertStatisticTypeEnum of(Integer code) {
        return Arrays.stream(values()).filter(e -> e.code.equals(code)).findFirst().orElse(null);
    }
}
