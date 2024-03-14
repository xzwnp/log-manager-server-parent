package com.example.xiao.logmanager.server.alert.strategy;

import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class TriggerImmediatelyLogAlertStrategy implements LogAlertStrategy {
    @Override
    public AlertStatisticTypeEnum getSupportedAlertType() {
        return AlertStatisticTypeEnum.TRIGGER_IMMEDIATELY;
    }
}
