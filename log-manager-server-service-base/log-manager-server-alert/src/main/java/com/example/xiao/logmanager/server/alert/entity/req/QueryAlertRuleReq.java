package com.example.xiao.logmanager.server.alert.entity.req;

import com.example.xiao.logmanager.server.alert.enums.AlertLevelEnum;
import com.example.xiao.logmanager.server.common.entity.req.PageRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QueryAlertRuleReq extends PageRequest {
    @NotBlank
    private String appName;
    @NotBlank
    private String group;

    private Long ruleId;

    private String ruleName;

    private AlertLevelEnum level;
}
