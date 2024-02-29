package com.example.xiao.logmanager.server.common.exception;

import com.example.xiao.logmanager.server.common.enums.ResultCode;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import lombok.NonNull;

public class NoPermissionException extends BizException {
    public NoPermissionException(String message) {
        super(ResultCode.NO_PERMISSION, message);
    }

    public NoPermissionException(@NonNull RoleEnum requiredRole) {
        super(ResultCode.NO_PERMISSION, "缺少角色:" + requiredRole.getName());
    }
}
