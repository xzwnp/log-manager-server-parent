package com.example.xiao.logmanager.server.alert.entity.dto;

import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixedWindowCountAlertCondition extends AlertCondition {
    protected AlertStatisticTypeEnum type = AlertStatisticTypeEnum.FIXED_WINDOW_COUNT;
    /**
     * 窗口周期,单位秒
     */
    private int cycle;

    /**
     * 触发阈值
     */
    private int threshold;

    @Override
    public String toString() {
        return "[固定窗口计数]最近%s秒内,关键字命中次数超过%s次".formatted(cycle, threshold);
    }
}
