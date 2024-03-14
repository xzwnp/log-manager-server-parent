package com.example.xiao.logmanager.server.user.service.biz;

import com.example.xiao.logmanager.server.common.enums.ResultCode;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.exception.BizException;
import com.example.xiao.logmanager.server.common.exception.NoPermissionException;
import com.example.xiao.logmanager.server.common.util.UserThreadLocalUtil;
import com.example.xiao.logmanager.server.common.util.jwt.JwtEntity;
import com.example.xiao.logmanager.server.user.entity.po.SysAppPo;
import com.example.xiao.logmanager.server.user.service.data.SysAppService;
import com.example.xiao.logmanager.server.user.service.data.SysUserAppRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPermissionService {
    private final SysUserAppRoleService userAppRoleService;
    private final SysAppService sysAppService;

    public void validateRole(RoleEnum requiredRole) {
        JwtEntity userInfo = UserThreadLocalUtil.getUserInfo();
        if (userInfo == null) {
            throw new BizException(ResultCode.UN_AUTH, "用户未登录");
        }
        List<String> roles = userInfo.getRoles();
        if (!roles.contains(requiredRole.getName())) {
            throw new NoPermissionException("无权限,需要角色" + requiredRole.getName());
        }
    }

    public void validateRole(RoleEnum requiredRole, Long appId) {
        JwtEntity userInfo = UserThreadLocalUtil.getUserInfo();
        validateRole(requiredRole, userInfo.getUserId(), appId);
    }

    public void validateRole(RoleEnum requiredRole, Long userId, Long appId) {
        List<String> roles = userAppRoleService.getRolesByUserIdAndAppId(userId, appId);
        if (!roles.contains(requiredRole.getName())) {
            throw new NoPermissionException("无权限,需要角色" + requiredRole.getName());
        }
    }

    public void validateRole(RoleEnum requiredRole, Long userId, String appName) {
        SysAppPo app = sysAppService.getByAppName(appName);
        if (app == null) {
            throw new BizException(ResultCode.DATA_NOT_EXIST, "应用不存在");
        }
        validateRole(requiredRole, userId, app.getId());
    }
}
