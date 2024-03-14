package com.example.xiao.logmanager.server.alert.strategy;

import com.example.xiao.logmanager.server.alert.entity.dto.FixedWindowPercentAlertCondition;
import com.example.xiao.logmanager.server.alert.entity.es.AppLogEsDocument;
import com.example.xiao.logmanager.server.alert.entity.po.AlertRulePo;
import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import com.example.xiao.logmanager.server.alert.util.FailureRateBasedFixedWindow;
import com.example.xiao.logmanager.server.common.util.KeyUtil;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FixedWindowPercentLogAlertStrategy implements LogAlertStrategy {
    private Map<String, FailureRateBasedFixedWindow> counterMap = new ConcurrentHashMap<>();


    @Override
    public void preHandle(AppLogEsDocument appLog, AlertRulePo rule) {
        FixedWindowPercentAlertCondition condition = (FixedWindowPercentAlertCondition) rule.getAlertConditionObj();
        Pattern pattern = Pattern.compile(condition.getDenominatorExpression());
        Matcher matcher = pattern.matcher(appLog.getMessage());
        //增加成功计数
        if (matcher.find()) {
            String key = KeyUtil.generateAppGroupKey(appLog.getAppName(), appLog.getGroup());
            FailureRateBasedFixedWindow fixedWindow = counterMap.computeIfAbsent(key, (k) -> new FailureRateBasedFixedWindow(condition.getCycle(), TimeUnit.SECONDS, condition.getThreshold()));
            fixedWindow.incrTotal();
        }

    }

    @Override
    public AlertStatisticTypeEnum getSupportedAlertType() {
        return AlertStatisticTypeEnum.FIXED_WINDOW_COUNT;
    }


    @Override
    public boolean needAlertIfMatch(AppLogEsDocument appLog, AlertRulePo rule) {
        String key = KeyUtil.generateAppGroupKey(appLog.getAppName(), appLog.getGroup());
        FailureRateBasedFixedWindow fixedWindow = counterMap.get(key);
        return fixedWindow.incrFailureAndJudge();
    }
}
