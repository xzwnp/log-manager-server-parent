package com.example.xiao.logmanager.server.alert.entity.req;

import com.example.xiao.logmanager.server.alert.enums.AlertLevelEnum;
import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AddAlertRuleReq {
    /**
     * 所属应用
     */
    @NotBlank
    private String appName;

    /**
     * 所属应用分组
     */
    @NotBlank
    private String group;
    /**
     * 规则名称
     */
    @NotBlank
    private String ruleName;

    /**
     * 告警级别
     */
    @NotNull
    private AlertLevelEnum level;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 匹配条件
     */
    @NotBlank
    private String matchCondition;

    /**
     * 统计策略 1立即告警 2固定窗口计数 3:滑动窗口计数 4:固定窗口百分比
     */
    @NotNull
    private AlertStatisticTypeEnum statisticType;

    /**
     * 告警条件,包括时间、阈值等
     */
    @NotNull
    private ObjectNode alertCondition;

    /**
     * 告警接收人
     */
    @NotBlank
    private String alertReceiver;

}
