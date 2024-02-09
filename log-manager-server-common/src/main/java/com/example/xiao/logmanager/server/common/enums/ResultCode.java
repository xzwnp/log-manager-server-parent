package com.example.xiao.logmanager.server.common.enums;


/**
 * com.atguigu.commonutils
 *
 * @author xzwnp
 * 2022/1/26
 * 21:52
 * Steps：
 */
public enum ResultCode {
    //    Integer SUCCESS = 20000;
//    Integer ERROR = 20001;
//    //支付中
//    Integer PAYING = 25000;
    SUCCESS(20000), ERROR(20001), INPUT_ERROR(20002),
    PATH_NOT_EXIST(20005),
    TOKEN_ERROR(28003),
    NO_PERMISSION(28004),
    ;
    private Integer code;

    private ResultCode(Integer code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code.toString();
    }
}
