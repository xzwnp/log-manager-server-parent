package com.example.xiao.logmanager.server.user.controller;

import com.example.xiao.log.annotation.LogRecord;
import com.example.xiao.log.annotation.LogRecordTrace;
import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.util.UserThreadLocalUtil;
import com.example.xiao.logmanager.server.user.entity.req.QueryAppReq;
import com.example.xiao.logmanager.server.user.entity.req.UpdateAppInfoReq;
import com.example.xiao.logmanager.server.user.entity.resp.QueryAppResp;
import com.example.xiao.logmanager.server.user.service.biz.AppBizService;
import com.example.xiao.logmanager.server.user.service.biz.UserPermissionService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/sys/app")
@RequiredArgsConstructor
@LogRecordTrace
@Validated
public class AppAdminController {
    private final AppBizService appBizService;
    private final UserPermissionService permissionService;

    @PostMapping("query")
    public R<PageDto<QueryAppResp>> queryApps(@RequestBody @Validated QueryAppReq req) {
        return R.success(appBizService.queryApps(req));
    }

    @PostMapping("detail")
    public R<QueryAppResp> queryAppDetail(@NotBlank String appName) {
        return R.success(appBizService.queryAppDetail(appName));
    }

    /**
     * 查询有权限的应用
     *
     * @return
     */
    @GetMapping("list")
    public R<Map<String, Set<String>>> listApps() {
        String username = UserThreadLocalUtil.getUserName();
        return R.success(appBizService.listApps(username));
    }

    @PostMapping("import")
    @LogRecord(operate = "导入应用")
    public R<Void> importApps() {
        permissionService.validateRole(RoleEnum.SYS_ADMIN);
        appBizService.importApps();
        return R.success();
    }

    @PostMapping("delete")
    public R<Void> deleteApp(@NotBlank String appName) {
        permissionService.validateRole(RoleEnum.SYS_ADMIN);
        appBizService.deleteApp(appName);
        return R.success();
    }

    @PostMapping("modifyAppAdmin")
    @LogRecord(operate = "修改应用管理员", content = "#isAdd?('将'+#username+'设置为应用管理员'):('取消'+#username +'的应用管理员')")
    public R<Void> modifyAppAdmin(@NotBlank String username, @NotBlank String appName, @NotNull Boolean isAdd) {
        permissionService.validateRole(RoleEnum.SYS_ADMIN);
        appBizService.modifyAppUser(username, appName, RoleEnum.APP_ADMIN, isAdd);
        return R.success();
    }

    @PostMapping("modifyAppUser")
    @LogRecord(operate = "修改应用成员", content = "#isAdd?('将'+#username+'添加为应用成员'):('取消'+#username +'的应用成员')")
    public R<Void> modifyAppUser(@NotBlank String username, @NotBlank String appName, @NotNull Boolean isAdd) {
        appBizService.modifyAppUser(username, appName, RoleEnum.APP_USER, isAdd);
        return R.success();
    }

    @PostMapping("modifyAppInfo")
    @LogRecord(operate = "修改应用基本信息")
    public R<Void> modifyAppInfo(@RequestBody @Validated UpdateAppInfoReq req) {
        appBizService.modifyAppInfo(req);
        return R.success();
    }
}
