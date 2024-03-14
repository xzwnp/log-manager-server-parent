package com.example.xiao.logmanager.server.alert.strategy;

import com.example.xiao.logmanager.server.alert.entity.es.AppLogEsDocument;
import com.example.xiao.logmanager.server.alert.entity.po.AlertRulePo;
import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public interface LogAlertStrategy {

    AlertStatisticTypeEnum getSupportedAlertType();

    /**
     * 前置预处理
     * @param appLog
     * @param rule
     */
    default void preHandle(AppLogEsDocument appLog, AlertRulePo rule) {

    }

    /**
     * 检验日志是否匹配告警规则
     *
     * @param appLog
     * @param rule
     * @return
     */
    default boolean match(AppLogEsDocument appLog, AlertRulePo rule) {
        String message = appLog.getMessage();
        String matchCondition = rule.getMatchCondition();
        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(matchCondition);
        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(message);
        return matcher.find();
    }

    /**
     * 规则命中后，是否需要触发告警
     *
     * @param appLog
     * @return
     */
    default boolean needAlertIfMatch(AppLogEsDocument appLog, AlertRulePo rule) {
        return true;
    }
}
