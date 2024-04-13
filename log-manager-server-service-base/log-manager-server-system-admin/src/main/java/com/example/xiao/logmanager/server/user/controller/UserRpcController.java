package com.example.xiao.logmanager.server.user.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.example.xiao.log.annotation.LogRecordTrace;
import com.example.xiao.logmanager.api.resp.UserInfoRpcResp;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.exception.BizException;
import com.example.xiao.logmanager.server.common.exception.NoPermissionException;
import com.example.xiao.logmanager.server.user.entity.resp.UserInfoResp;
import com.example.xiao.logmanager.server.user.service.biz.UserBizService;
import com.example.xiao.logmanager.server.user.service.biz.UserPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rpc/sys/user")
@LogRecordTrace
@RequiredArgsConstructor
public class UserRpcController {
    private final UserPermissionService permissionService;
    private final UserBizService userBizService;

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

    /**
     * 查询用户信息
     */
    @GetMapping("info")
    public R<UserInfoRpcResp> getUserInfo(String username) {
        List<UserInfoRpcResp> userInfoRespList = userBizService.queryUserInfoBatch(List.of(username));
        UserInfoRpcResp userInfo = CollectionUtil.isNotEmpty(userInfoRespList) ? userInfoRespList.get(0) : null;
        return R.success(userInfo);
    }

    /**
     * 查询用户信息
     */
    @GetMapping("info/batch")
    public R<List<UserInfoRpcResp>> getUserInfo(@RequestParam List<String> usernameList) {
        List<UserInfoRpcResp> userInfoRespList = userBizService.queryUserInfoBatch(usernameList);
        return R.success(userInfoRespList);
    }
}
