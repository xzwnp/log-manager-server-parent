package com.example.xiao.logmanager.server.alert.strategy;

import com.example.xiao.logmanager.server.alert.entity.dto.FixedWindowCountAlertCondition;
import com.example.xiao.logmanager.server.alert.entity.es.AppLogEsDocument;
import com.example.xiao.logmanager.server.alert.entity.po.AlertRulePo;
import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import com.example.xiao.logmanager.server.alert.util.FixedWindow;
import com.example.xiao.logmanager.server.common.util.KeyUtil;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class FixedWindowCountLogAlertStrategy implements LogAlertStrategy {
    private Map<String, FixedWindow> counterMap = new ConcurrentHashMap<>();

    @Override
    public AlertStatisticTypeEnum getSupportedAlertType() {
        return AlertStatisticTypeEnum.FIXED_WINDOW_COUNT;
    }


    @Override
    public boolean needAlertIfMatch(AppLogEsDocument appLog, AlertRulePo rule) {
        FixedWindowCountAlertCondition condition = (FixedWindowCountAlertCondition) rule.getAlertConditionObj();
        String key = KeyUtil.generateAppGroupKey(appLog.getAppName(), appLog.getGroup());
        FixedWindow fixedWindow = counterMap.computeIfAbsent(key, (k) -> new FixedWindow(condition.getCycle(), TimeUnit.SECONDS, condition.getThreshold()));
        return fixedWindow.incrAndJudge();
    }
}
