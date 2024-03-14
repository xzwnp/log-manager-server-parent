package com.example.xiao.logmanager.server.alert.entity.dto;

import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import lombok.Data;

@Data
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
}
