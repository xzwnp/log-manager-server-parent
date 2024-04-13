package com.example.xiao.logmanager.server.alert.service.biz;

import com.example.xiao.logmanager.api.enums.NotificationTypeEnum;
import com.example.xiao.logmanager.api.feign.MessagingFeignClient;
import com.example.xiao.logmanager.api.req.SendMessageRequest;
import com.example.xiao.logmanager.server.alert.entity.es.AppLogEsDocument;
import com.example.xiao.logmanager.server.alert.entity.po.AlertHistoryPo;
import com.example.xiao.logmanager.server.alert.entity.po.AlertRulePo;
import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import com.example.xiao.logmanager.server.alert.factory.AlertRuleFactory;
import com.example.xiao.logmanager.server.alert.factory.LogAlertStrategyFactory;
import com.example.xiao.logmanager.server.alert.service.AlertHistoryService;
import com.example.xiao.logmanager.server.alert.strategy.LogAlertStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分析日志是否存在异常,是否需要触发告警
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LogAnalyzeService {
    private final AlertRuleFactory alertRuleFactory;
    private final LogAlertStrategyFactory logAlertStrategyFactory;
    private final AlertHistoryService alertHistoryService;
    private final MessagingFeignClient messagingFeignClient;

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
            if (appLog.getMessage() == null) {
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
                    log.error("规则命中,触发告警,规则关键字:{},统计方法:{},触发日志:{}", rule.getMatchCondition(), rule.getAlertConditionObj(), appLog.getMessage());
                    AlertHistoryPo alertHistory = saveAlertHistory(appLog, rule);
                    sendAlert(alertHistory, appLog, rule);
                }
            }
        }
    }

    private AlertHistoryPo saveAlertHistory(AppLogEsDocument appLog, AlertRulePo rule) {
        String alertDescription = "满足匹配关键字[%s],统计条件[%s]".formatted(rule.getMatchCondition(), rule.getAlertConditionObj());
        AlertHistoryPo alertHistoryPo = new AlertHistoryPo()
                .setAppName(rule.getAppName())
                .setAppGroup(rule.getAppGroup())
                .setRuleId(rule.getId())
                .setRuleName(rule.getRuleName())
                .setLevel(rule.getLevel())
                .setRuleDescription(rule.getDescription())
                .setAlertDescription(alertDescription)
                .setAlertReceiver(rule.getAlertReceiver());
        alertHistoryService.save(alertHistoryPo);
        return alertHistoryPo;
    }

    private void sendAlert(AlertHistoryPo alertHistory, AppLogEsDocument appLog, AlertRulePo rule) {
        try {
            List<String> alertReceivers = Arrays.stream(rule.getAlertReceiver().split(",")).toList();
            String title = "日志中心告警-%s-%s-%s".formatted(rule.getLevel().getDesc(), rule.getAppName(), rule.getAppGroup());
            String content = "命中告警规则:%s\n告警信息:%s\n告警接收人:%s\n".formatted(alertHistory.getRuleDescription(), alertHistory.getAlertDescription(), rule.getAlertReceiver());
            List<NotificationTypeEnum> notificationTypes = rule.getNotificationTypes().stream().map(NotificationTypeEnum::of).toList();
            SendMessageRequest request = SendMessageRequest.builder().toUsers(alertReceivers).title(title).content(content).notificationTypes(notificationTypes).build();
            messagingFeignClient.sendMessage(request);
        } catch (Exception e) {
            log.error("Send Alert Message Fail", e);
        }
    }

}
