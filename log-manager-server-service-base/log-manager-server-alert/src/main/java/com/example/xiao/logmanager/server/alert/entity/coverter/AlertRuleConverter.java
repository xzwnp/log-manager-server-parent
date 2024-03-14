package com.example.xiao.logmanager.server.alert.entity.coverter;

import com.example.xiao.logmanager.server.alert.entity.po.AlertRulePo;
import com.example.xiao.logmanager.server.alert.entity.req.AddAlertRuleReq;
import com.example.xiao.logmanager.server.alert.entity.req.EditAlertRuleReq;
import com.example.xiao.logmanager.server.alert.entity.resp.QueryAlertRuleResp;
import com.example.xiao.logmanager.server.common.util.JsonUtil;

import java.util.Arrays;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = {JsonUtil.class, Arrays.class})
public interface AlertRuleConverter {
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "appGroup", source = "group")
    @Mapping(target = "deleteFlag", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "updaterId", ignore = true)
    @Mapping(target = "updatedTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alertCondition", expression = "java(JsonUtil.toJson(req.getAlertCondition()))")
    AlertRulePo addReq2Po(AddAlertRuleReq req);

    @Mapping(target = "updaterId", ignore = true)
    @Mapping(target = "updatedTime", ignore = true)
    @Mapping(target = "deleteFlag", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "appName", ignore = true)
    @Mapping(target = "appGroup", ignore = true)
    @Mapping(target = "alertCondition", expression = "java(JsonUtil.toJson(req.getAlertCondition()))")
    AlertRulePo editReq2Po(EditAlertRuleReq req);

    @Mapping(target = "alertCondition", expression = "java(JsonUtil.fromJson(po.getAlertCondition()))")
    QueryAlertRuleResp po2QueryResp(AlertRulePo po);

    List<QueryAlertRuleResp> poList2QueryRespList(List<AlertRulePo> poList);
}
