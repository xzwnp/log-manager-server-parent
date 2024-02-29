package com.example.xiao.logmanager.server.user.service.biz;

import com.example.xiao.logmanager.server.common.enums.ResultCode;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.exception.BizException;
import com.example.xiao.logmanager.server.common.exception.NoPermissionException;
import com.example.xiao.logmanager.server.common.util.UserThreadLocalUtil;
import com.example.xiao.logmanager.server.common.util.jwt.JwtEntity;
import com.example.xiao.logmanager.server.user.service.data.SysUserAppRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPermissionService {
    private final SysUserAppRoleService userAppRoleService;

    public void validateRole(RoleEnum requiredRole) {
        JwtEntity userInfo = UserThreadLocalUtil.getUserInfo();
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
}
