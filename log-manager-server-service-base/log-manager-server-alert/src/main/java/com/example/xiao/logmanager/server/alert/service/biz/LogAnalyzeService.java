package com.example.xiao.logmanager.server.alert.service.biz;

import com.example.xiao.logmanager.server.alert.entity.es.AppLogEsDocument;
import com.example.xiao.logmanager.server.alert.entity.po.AlertRulePo;
import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import com.example.xiao.logmanager.server.alert.factory.AlertRuleFactory;
import com.example.xiao.logmanager.server.alert.factory.LogAlertStrategyFactory;
import com.example.xiao.logmanager.server.alert.strategy.LogAlertStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分析日志是否存在异常,是否需要触发告警
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LogAnalyzeService {
    private final AlertRuleFactory alertRuleFactory;
    private final LogAlertStrategyFactory logAlertStrategyFactory;

    public void analyze(AppLogEsDocument appLog) {
        List<AlertRulePo> rules = alertRuleFactory.getAlertRules(appLog.getAppName(), appLog.getGroup());
        for (AlertRulePo rule : rules) {
            //跳过已禁用的、被静默的告警
            if (Boolean.FALSE.equals(rule.getEnabled())) {
                continue;
            }
            if (rule.getMuteRevertTime() != null && rule.getMuteRevertTime().isAfter(LocalDateTime.now())) {
                continue;
            }
            AlertStatisticTypeEnum statisticType = rule.getStatisticType();
            LogAlertStrategy logAlertStrategy = logAlertStrategyFactory.getLogAlertStrategy(statisticType);
            //预处理
            logAlertStrategy.preHandle(appLog, rule);
            //判断是否命中
            if (logAlertStrategy.match(appLog, rule)) {
                //如果命中,判断是否需要告警
                if (logAlertStrategy.needAlertIfMatch(appLog, rule)) {
                    log.info("命中,触发告警,规则关键字:{},统计方法:{},触发日志:{}", rule.getMatchCondition(), rule.getAlertConditionObj(), appLog.getMessage());
                }
            }
        }
    }

}
