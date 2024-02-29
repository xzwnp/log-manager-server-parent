package com.example.xiao.logmanager.server.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResultCode {
    SUCCESS(0, "成功"),
    ERROR(1001, "系统异常"),
    BIZ_EXCEPTION(1002, "业务逻辑错误"),
    DATA_NOT_EXIST(1003, "数据不存在"),
    DATA_ALREADY_EXIST(1004, "数据已存在"),
    PARAM_ERROR(2001, "请求参数错误"),
    UN_AUTH(3001, "Token无效"),
    NO_PERMISSION(3002, "无权限"),
    PATH_NOT_EXIST(4001, "请求路径不存在"),
    ;
    private final Integer code;
    private final String message;

}
