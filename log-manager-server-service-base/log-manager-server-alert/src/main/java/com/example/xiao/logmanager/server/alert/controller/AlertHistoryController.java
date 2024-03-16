package com.example.xiao.logmanager.server.alert.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.xiao.logmanager.server.alert.entity.po.AlertHistoryPo;
import com.example.xiao.logmanager.server.alert.entity.req.QueryAlertHistoryReq;
import com.example.xiao.logmanager.server.alert.service.AlertHistoryService;
import com.example.xiao.logmanager.server.alert.service.AlertHistoryService;
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
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-03-10
 */
@RestController
@RequestMapping("api/alert/history")
@RequiredArgsConstructor
@Validated
public class AlertHistoryController {
    private final PermissionBizService permissionBizService;

    private final AlertHistoryService alertHistoryService;

    @PostMapping("query")
    public R<PageDto<AlertHistoryPo>> queryAlertHistory(@RequestBody @Validated QueryAlertHistoryReq req) {
        permissionBizService.validPermission(RoleEnum.APP_USER, req.getAppName());
        PageDto<AlertHistoryPo> resp = new PageDto<>(req);
        Page<AlertHistoryPo> page = new Page<>(req.getCurrent(), req.getSize());
        alertHistoryService.lambdaQuery()
                .eq(AlertHistoryPo::getAppName, req.getAppName())
                .eq(AlertHistoryPo::getAppGroup, req.getGroup())
                .eq(req.getRuleId() != null, AlertHistoryPo::getAppGroup, req.getGroup())
                .like(StringUtils.isNotBlank(req.getRuleName()), AlertHistoryPo::getRuleName, req.getRuleName())
                .eq(req.getLevel() != null, AlertHistoryPo::getLevel, req.getLevel())
                .ge(req.getStartTime() != null, AlertHistoryPo::getCreatedTime, req.getStartTime())
                .le(req.getEndTime() != null, AlertHistoryPo::getCreatedTime, req.getStartTime())
                .page(page);
        resp.setTotal(page.getTotal());
        resp.setRecords(page.getRecords());
        return R.success(resp);
    }


}
