package com.example.xiao.logmanager.server.user.controller;

import com.example.xiao.log.annotation.LogRecordTrace;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.exception.NoPermissionException;
import com.example.xiao.logmanager.api.req.ValidatePermissionReq;
import com.example.xiao.logmanager.server.user.service.biz.UserPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rpc/sys/user")
@LogRecordTrace
@RequiredArgsConstructor
public class UserPermissionController {
    private final UserPermissionService permissionService;

    /**
     * 校验用户是否具有操作应用的权限
     */
    @PostMapping("app-permission/validate")
    public R<Boolean> validateAppPermission(@RequestBody ValidatePermissionReq req) {
        RoleEnum requiredRole = RoleEnum.of(req.getRequiredRole());
        boolean valid = true;
        try {
            permissionService.validateRole(requiredRole, req.getUserId(), req.getAppId());
        } catch (NoPermissionException e) {
            valid = false;
        }
        return R.success(valid);
    }
}
