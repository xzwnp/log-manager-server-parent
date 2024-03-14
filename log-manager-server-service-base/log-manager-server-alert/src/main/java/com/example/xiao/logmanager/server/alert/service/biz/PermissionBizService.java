package com.example.xiao.logmanager.server.alert.service.biz;

import com.example.xiao.logmanager.api.feign.SystemAdminFeignClient;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.exception.NoPermissionException;
import com.example.xiao.logmanager.server.common.util.UserThreadLocalUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionBizService {
    private final SystemAdminFeignClient systemAdminFeignClient;

    public void validPermission(@NonNull RoleEnum roleEnum, @NonNull String appName) {
        Long userId = UserThreadLocalUtil.getUserId();
        R<Boolean> permissionResult = systemAdminFeignClient.validateAppPermission(userId, appName, roleEnum.getCode());
        if (!permissionResult.getData()) {
            throw new NoPermissionException(roleEnum);
        }
    }

}
