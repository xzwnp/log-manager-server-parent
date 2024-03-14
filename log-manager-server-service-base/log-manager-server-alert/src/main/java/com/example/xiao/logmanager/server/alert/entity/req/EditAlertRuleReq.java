package com.example.xiao.logmanager.server.alert.entity.req;

import com.example.xiao.logmanager.server.alert.enums.AlertLevelEnum;
import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class EditAlertRuleReq {
    /**
     * 规则id
     */
    @NotNull
    private Long id;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 告警级别
     */
    private AlertLevelEnum level;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 匹配条件
     */
    private String matchCondition;

    /**
     * 统计策略 1立即告警 2固定窗口计数 3:滑动窗口计数 4:固定窗口百分比
     */
    private AlertStatisticTypeEnum statisticType;

    /**
     * 告警条件,包括时间、阈值等
     */
    private ObjectNode alertCondition;

    /**
     * 告警接收人
     */
    private String alertReceiver;

}
