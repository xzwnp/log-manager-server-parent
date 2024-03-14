package com.example.xiao.logmanager.server.user.controller;

import com.example.xiao.log.annotation.LogRecordTrace;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.exception.BizException;
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
    public R<Boolean> validateAppPermission(Long userId, String appName, Integer roleId) {
        RoleEnum role = RoleEnum.of(roleId);
        boolean valid = true;
        try {
            permissionService.validateRole(role, userId, appName);
        } catch (NoPermissionException e) {
            valid = false;
        } catch (BizException e) {
            return R.error(e.getResultCode(), e.getMessage());
        }
        return R.success(valid);
    }

    /**
     * 校验用户是否具有操作应用的权限
     */
    @PostMapping("app-permission/validate-v2")
    public R<Boolean> validateAppPermission(Long userId, Long appId, Integer roleId) {
        RoleEnum role = RoleEnum.of(roleId);
        boolean valid = true;
        try {
            permissionService.validateRole(role, userId, appId);
        } catch (NoPermissionException e) {
            valid = false;
        }
        return R.success(valid);
    }
}
