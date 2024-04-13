package com.example.xiao.logmanager.api.feign;


import com.example.xiao.logmanager.api.resp.UserInfoRpcResp;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @GetMapping("/rpc/sys/user/info/batch")
    R<List<UserInfoRpcResp>> getUserInfoBatch(@RequestParam List<String> usernameList);

    @GetMapping("/rpc/sys/user/info")
    R<UserInfoRpcResp> getUserInfo(@RequestParam String username);
}
