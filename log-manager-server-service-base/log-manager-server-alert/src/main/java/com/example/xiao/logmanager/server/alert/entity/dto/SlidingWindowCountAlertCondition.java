package com.example.xiao.logmanager.server.alert.entity.dto;

import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlidingWindowCountAlertCondition extends AlertCondition {
    protected AlertStatisticTypeEnum type = AlertStatisticTypeEnum.SLIDING_WINDOW_COUNT;
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
        return "[滑动窗口计数]最近%d秒内,关键字命中次数超过%d次".formatted(cycle, threshold);
    }
}
