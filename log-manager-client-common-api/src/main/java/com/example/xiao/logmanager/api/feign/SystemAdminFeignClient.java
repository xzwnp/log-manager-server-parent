package com.example.xiao.logmanager.api.feign;


import com.example.xiao.logmanager.server.common.entity.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("log-manager-system-admin")
@Component
public interface SystemAdminFeignClient {
    /**
     * 校验用户是否具有操作应用的权限
     */
    @PostMapping("/rpc/sys/user/app-permission/validate")
    R<Boolean> validateAppPermission(@RequestParam Long userId, @RequestParam String appName, @RequestParam Integer roleId);

    /**
     * 校验用户是否具有操作应用的权限
     */
    @PostMapping("/rpc/sys/user/app-permission/validate-v2")
    R<Boolean> validateAppPermission(@RequestParam Long userId, @RequestParam Long appId, @RequestParam Integer roleId);

}
