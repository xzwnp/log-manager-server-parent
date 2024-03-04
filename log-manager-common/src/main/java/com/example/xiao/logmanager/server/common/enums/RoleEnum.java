package com.example.xiao.logmanager.server.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum RoleEnum implements BaseEnum {
    SYS_ADMIN(1, "系统管理员"), SYS_USER(2, "普通用户"), APP_ADMIN(11, "应用管理员"), APP_USER(12, "应用使用者"),
    ;
    @JsonValue
    private final Integer code;
    @Getter
    private final String desc;


    @Override
    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name().toLowerCase();
    }

    //根据code获取枚举对象
    public static RoleEnum of(Integer code) {
        return Arrays.stream(RoleEnum.values()).filter(e -> e.code.equals(code)).findFirst().orElse(null);
    }

    //根据name获取枚举对象
    public static RoleEnum of(String name) {
        return Arrays.stream(RoleEnum.values()).filter(e -> e.name().toLowerCase().equals(name)).findFirst().orElse(null);
    }

}
