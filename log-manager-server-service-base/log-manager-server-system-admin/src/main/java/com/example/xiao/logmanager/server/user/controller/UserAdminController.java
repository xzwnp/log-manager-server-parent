package com.example.xiao.logmanager.server.user.controller;

import com.example.xiao.log.annotation.LogRecord;
import com.example.xiao.log.annotation.LogRecordTrace;
import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.user.entity.req.AddUserReq;
import com.example.xiao.logmanager.server.user.entity.req.ChangePasswordReq;
import com.example.xiao.logmanager.server.user.entity.req.QueryUserReq;
import com.example.xiao.logmanager.server.user.entity.req.UserLoginReq;
import com.example.xiao.logmanager.server.user.entity.resp.AddUserResp;
import com.example.xiao.logmanager.server.user.entity.resp.RefreshTokenResp;
import com.example.xiao.logmanager.server.user.entity.resp.UserInfoResp;
import com.example.xiao.logmanager.server.user.entity.resp.UserLoginResp;
import com.example.xiao.logmanager.server.user.service.biz.UserSysService;
import com.example.xiao.logmanager.server.user.service.biz.UserPermissionService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@LogRecordTrace
@Validated
public class UserAdminController {

    private final UserSysService userSysService;
    private final UserPermissionService permissionService;


    @PostMapping("login")
    @LogRecord(operate = "登录")
    public R<UserLoginResp> login(@RequestBody UserLoginReq req) {
        return R.success(userSysService.login(req));
    }

    @PostMapping("refreshToken")
    @LogRecord(operate = "刷新token")
    public R<RefreshTokenResp> refreshToken() {
        return R.success(userSysService.refreshToken());
    }


    @PostMapping("query")
    @LogRecord(operate = "查询用户")
    public R<PageDto<UserInfoResp>> addUser(@RequestBody QueryUserReq req) {
        permissionService.validateRole(RoleEnum.SYS_ADMIN);

        PageDto<UserInfoResp> resp = userSysService.queryUser(req);
        return R.success(resp);
    }


    @PostMapping("add")
    @LogRecord(operate = "添加用户")
    public R<AddUserResp> addUser(@RequestBody AddUserReq req) {
        permissionService.validateRole(RoleEnum.SYS_ADMIN);

        AddUserResp resp = userSysService.addUser(req);
        return R.success(resp);
    }

    @PostMapping("modifySysAdminRole")
    @LogRecord(operate = "修改系统管理员角色",content = "#isAdd?('将'+#username+'设置为系统管理员'):('取消'+#username +'的系统管理员')")
    public R<Void> modifySysAdminRole(@NotBlank String username, @NotNull Boolean isAdd) {
        permissionService.validateRole(RoleEnum.SYS_ADMIN);
        userSysService.modifySysAdminRole(username,isAdd);
        return R.success();
    }

    @PostMapping("delete")
    @LogRecord(operate = "删除用户")
    public R<Void> deleteUser(String username) {
        permissionService.validateRole(RoleEnum.SYS_ADMIN);

        userSysService.removeUser(username);
        return R.success();
    }

    @PostMapping("changePassword")
    @LogRecord(operate = "修改密码")
    public R<Void> changePassword(@RequestBody ChangePasswordReq req) {
        userSysService.changePassword(req);
        return R.success();
    }
}
