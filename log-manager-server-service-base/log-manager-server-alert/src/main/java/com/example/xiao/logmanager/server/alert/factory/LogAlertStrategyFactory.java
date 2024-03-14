package com.example.xiao.logmanager.server.alert.factory;

import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import com.example.xiao.logmanager.server.alert.strategy.LogAlertStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LogAlertStrategyFactory {
    private final Map<AlertStatisticTypeEnum, LogAlertStrategy> strategyMap;

    public LogAlertStrategyFactory(List<LogAlertStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(LogAlertStrategy::getSupportedAlertType, o -> o));
    }

    public LogAlertStrategy getLogAlertStrategy(AlertStatisticTypeEnum alertStatisticTypeEnum) {
        return strategyMap.get(alertStatisticTypeEnum);
    }
}
