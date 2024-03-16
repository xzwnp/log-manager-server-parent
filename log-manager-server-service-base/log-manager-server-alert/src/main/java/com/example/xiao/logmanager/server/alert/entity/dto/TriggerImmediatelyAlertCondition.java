package com.example.xiao.logmanager.server.alert.entity.dto;

import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TriggerImmediatelyAlertCondition extends AlertCondition {
    protected AlertStatisticTypeEnum type = AlertStatisticTypeEnum.TRIGGER_IMMEDIATELY;

    @Override
    public String toString() {
        return "[立即触发]";
    }
}
