package com.example.xiao.logmanager.server.statistic.enums;

import com.example.xiao.logmanager.server.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OperateStatisticDimensionEnum implements BaseEnum {
    OPERATE_NAME(1, "操作名称"),
    OPERATE_COST(2, "操作耗时"),
    OPERATE_TIME(3, "操作时间"),
    OPERATE_COUNT(4, "调用次数");

    @JsonValue
    private final int code;

    private final String name;

    //code转枚举类
    public static OperateStatisticDimensionEnum of(int code) {
        for (OperateStatisticDimensionEnum e : OperateStatisticDimensionEnum.values()) {
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

