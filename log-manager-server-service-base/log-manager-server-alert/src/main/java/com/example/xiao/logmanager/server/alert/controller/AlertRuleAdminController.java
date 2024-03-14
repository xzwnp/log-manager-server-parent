package com.example.xiao.logmanager.server.alert.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.xiao.logmanager.server.alert.entity.coverter.AlertRuleConverter;
import com.example.xiao.logmanager.server.alert.entity.po.AlertRulePo;
import com.example.xiao.logmanager.server.alert.entity.req.AddAlertRuleReq;
import com.example.xiao.logmanager.server.alert.entity.req.EditAlertRuleReq;
import com.example.xiao.logmanager.server.alert.entity.req.QueryAlertRuleReq;
import com.example.xiao.logmanager.server.alert.entity.resp.QueryAlertRuleResp;
import com.example.xiao.logmanager.server.alert.factory.AlertRuleFactory;
import com.example.xiao.logmanager.server.alert.service.AlertRuleService;
import com.example.xiao.logmanager.server.alert.service.biz.PermissionBizService;
import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.enums.ResultCode;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-03-10
 */
@RestController
@RequestMapping("api/alert/rules")
@RequiredArgsConstructor
@Validated
public class AlertRuleAdminController {
    private final PermissionBizService permissionBizService;

    private final AlertRuleService alertRuleService;
    private final AlertRuleConverter alertRuleConverter;
    private final AlertRuleFactory alertRuleFactory;

    @PostMapping("query")
    public R<PageDto<QueryAlertRuleResp>> queryAlertRule(@RequestBody @Validated QueryAlertRuleReq req) {
        PageDto<QueryAlertRuleResp> resp = new PageDto<>(req);

        Page<AlertRulePo> page = new Page<>(req.getCurrent(), req.getSize());
        alertRuleService.lambdaQuery()
                .eq(AlertRulePo::getAppName, req.getAppName())
                .eq(AlertRulePo::getAppGroup, req.getGroup())
                .eq(req.getRuleId() != null, AlertRulePo::getAppGroup, req.getGroup())
                .like(StringUtils.isNotBlank(req.getRuleName()), AlertRulePo::getRuleName, req.getRuleName())
                .eq(req.getLevel() != null, AlertRulePo::getLevel, req.getLevel())
                .page(page);
        resp.setTotal(page.getTotal());

        List<QueryAlertRuleResp> queryAlertRuleRespList = alertRuleConverter.poList2QueryRespList(page.getRecords());
        resp.setRecords(queryAlertRuleRespList);

        return R.success(resp);
    }

    @PostMapping("add")
    public R<Void> addAlertRule(@RequestBody @Validated AddAlertRuleReq req) {
        //权限验证
        permissionBizService.validPermission(RoleEnum.APP_ADMIN, req.getAppName());
        AlertRulePo alertRulePo = alertRuleConverter.addReq2Po(req);
        alertRuleService.save(alertRulePo);
        //刷新缓存
        alertRuleFactory.refreshRule(alertRulePo);
        return R.success();
    }

    @PostMapping("edit")
    @Transactional
    public R<Void> addAlertRule(@RequestBody @Validated EditAlertRuleReq req) {
        AlertRulePo alertRulePo = alertRuleService.getById(req.getId());
        if (alertRulePo == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "告警规则不存在!");
        }
        //权限验证
        permissionBizService.validPermission(RoleEnum.APP_ADMIN, alertRulePo.getAppName());
        //
        alertRulePo = alertRuleConverter.editReq2Po(req);
        alertRuleService.updateById(alertRulePo);
        //刷新缓存
        alertRuleFactory.refreshRule(alertRulePo);
        return R.success();
    }

    @PostMapping("delete")
    @Transactional
    public R<Void> deleteAlertRule(Long alertRuleId) {
        AlertRulePo alertRulePo = alertRuleService.getById(alertRuleId);
        if (alertRulePo == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "告警规则不存在!");
        }
        //权限验证
        permissionBizService.validPermission(RoleEnum.APP_ADMIN, alertRulePo.getAppName());
        alertRuleService.removeById(alertRuleId);
        //刷新缓存
        alertRuleFactory.refreshRules();
        return R.success();
    }

    /**
     * 告警静默
     *
     * @param alertRuleId
     * @param duration    静默时长,单位秒
     * @return
     */
    @PostMapping("mute")
    @Transactional
    public R<Void> muteAlertRule(Long alertRuleId, long duration) {
        AlertRulePo alertRulePo = alertRuleService.getById(alertRuleId);
        if (alertRulePo == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "告警规则不存在!");
        }
        //权限验证
        permissionBizService.validPermission(RoleEnum.APP_USER, alertRulePo.getAppName());
        alertRulePo.setMuteRevertTime(LocalDateTime.now().plusSeconds(duration));
        alertRuleService.updateById(alertRulePo);
        //刷新缓存
        alertRuleFactory.refreshRule(alertRulePo);
        return R.success();
    }
}
