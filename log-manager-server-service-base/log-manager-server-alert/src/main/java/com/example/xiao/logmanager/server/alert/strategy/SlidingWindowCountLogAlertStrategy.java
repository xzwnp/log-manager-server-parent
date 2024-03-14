package com.example.xiao.logmanager.server.alert.strategy;

import com.example.xiao.logmanager.server.alert.entity.dto.SlidingWindowCountAlertCondition;
import com.example.xiao.logmanager.server.alert.entity.es.AppLogEsDocument;
import com.example.xiao.logmanager.server.alert.entity.po.AlertRulePo;
import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import com.example.xiao.logmanager.server.alert.util.SlidingWindow;
import com.example.xiao.logmanager.server.common.util.KeyUtil;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class SlidingWindowCountLogAlertStrategy implements LogAlertStrategy {
    private Map<String, SlidingWindow> counterMap = new ConcurrentHashMap<>();

    @Override
    public AlertStatisticTypeEnum getSupportedAlertType() {
        return AlertStatisticTypeEnum.SLIDING_WINDOW_COUNT;
    }


    @Override
    public boolean needAlertIfMatch(AppLogEsDocument appLog, AlertRulePo rule) {
        SlidingWindowCountAlertCondition condition = (SlidingWindowCountAlertCondition) rule.getAlertConditionObj();
        String key = KeyUtil.generateAppGroupKey(appLog.getAppName(), appLog.getGroup());
        SlidingWindow slidingWindow = counterMap.computeIfAbsent(key, (k) -> new SlidingWindow(condition.getCycle(), TimeUnit.SECONDS, condition.getThreshold()));
        return slidingWindow.incrAndJudge();
    }
}
