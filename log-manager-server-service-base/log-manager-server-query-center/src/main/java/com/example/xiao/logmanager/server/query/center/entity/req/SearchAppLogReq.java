package com.example.xiao.logmanager.server.query.center.entity.req;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.example.xiao.logmanager.server.common.entity.req.PageRequest;
import com.example.xiao.logmanager.server.query.center.enums.LogLevelEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * com.example.xiao.logmanager.server.query.center.entity.req
 *
 * @author xzwnp
 * 2024/1/26
 * 13:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchAppLogReq extends PageRequest {
    @NotBlank
    private String appName;
    @NotBlank
    private String group;
    private String keyword;
    private Boolean isTokenizeKeyword = false;
    private String negativeKeyword;
    private LogLevelEnum level;
    private String traceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean timeDesc = true;
}
