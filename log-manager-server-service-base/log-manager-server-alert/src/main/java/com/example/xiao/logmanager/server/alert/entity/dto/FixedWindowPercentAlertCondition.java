package com.example.xiao.logmanager.server.alert.entity.dto;

import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixedWindowPercentAlertCondition extends AlertCondition {
    protected AlertStatisticTypeEnum type = AlertStatisticTypeEnum.FIXED_WINDOW_PERCENT;
    /**
     * 窗口周期,单位秒
     */
    private int cycle;

    /**
     * 触发阈值
     */
    private double threshold;

    /**
     * 分母表达式
     */
    private String denominatorExpression;

    @Override
    public String toString() {
        return "[固定窗口百分比统计]最近%d秒内,关键字命中百分比达到%s".formatted(cycle, threshold);
    }
}
