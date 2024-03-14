package com.example.xiao.logmanager.server.alert.factory;

import com.example.xiao.logmanager.server.alert.entity.dto.*;
import com.example.xiao.logmanager.server.alert.entity.po.AlertRulePo;
import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import com.example.xiao.logmanager.server.alert.service.AlertRuleService;
import com.example.xiao.logmanager.server.common.util.JsonUtil;
import com.example.xiao.logmanager.server.common.util.KeyUtil;
import lombok.NonNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AlertRuleFactory implements InitializingBean {
    private final AlertRuleService alertRuleService;
    private Map<String, List<AlertRulePo>> alertRuleMap;

    public AlertRuleFactory(AlertRuleService alertRuleService) {
        this.alertRuleService = alertRuleService;
    }

    public List<AlertRulePo> getAlertRules(@NonNull String appName, @NonNull String group) {
        String key = appName + "::" + group;
        return alertRuleMap.getOrDefault(key, new ArrayList<>());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshRules();
    }

    private AlertCondition getAlertConditionObj(AlertStatisticTypeEnum type, String alertConditonString) {
        return switch (type) {
            case TRIGGER_IMMEDIATELY -> new TriggerImmediatelyAlertCondition();
            case FIXED_WINDOW_COUNT -> JsonUtil.fromJson(alertConditonString, FixedWindowCountAlertCondition.class);
            case SLIDING_WINDOW_COUNT -> JsonUtil.fromJson(alertConditonString, SlidingWindowCountAlertCondition.class);
            case FIXED_WINDOW_PERCENT -> JsonUtil.fromJson(alertConditonString, FixedWindowPercentAlertCondition.class);
        };
    }

    public void refreshRules() {
        List<AlertRulePo> alertRules = alertRuleService.list();
        alertRules.forEach(rule -> rule.setAlertConditionObj(getAlertConditionObj(rule.getStatisticType(), rule.getAlertCondition())));
        alertRuleMap = alertRules.stream().collect(Collectors.groupingBy(rule -> KeyUtil.generateAppGroupKey(rule.getAppName(), rule.getAppGroup())));
    }


    public void refreshRule(@NonNull AlertRulePo rule) {
        String key = KeyUtil.generateAppGroupKey(rule.getAppName(), rule.getAppGroup());
        List<AlertRulePo> rules = alertRuleMap.computeIfAbsent(key, (k) -> new ArrayList<>());
        //删除旧数据
        Iterator<AlertRulePo> iterator = rules.iterator();
        while (iterator.hasNext()) {
            AlertRulePo currentRule = iterator.next();
            if (currentRule.getId().equals(rule.getId())) {
                iterator.remove();
                break;
            }
        }
        //加入新数据
        rules.add(rule);
    }
}
