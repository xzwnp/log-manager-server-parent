package com.example.xiao.logmanager.server.query.center.entity.document;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperateLogEsDocument {
    /**
     * 应用名称
     */
    private String appName;

    /**
     * 分组
     */
    private String group;

    /**
     * 耗时
     */
    private Long timeCost;

    /**
     * 操作
     */
    private String operate;

    /**
     * 内容
     */
    private String content;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    /**
     * traceId
     */
    private String traceId;

    /**
     * 操作员ID
     */
    private String operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 参数
     */
    private String parameters;

    /**
     * 返回值
     */
    private String returnValue;

    /**
     * 异常
     */
    private String exception;
}
