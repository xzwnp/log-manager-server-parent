package com.example.xiao.logmanager.api.feign;


import com.example.xiao.logmanager.api.req.ValidatePermissionReq;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("log-manager-system-admin")
@Component
public interface SystemAdminFeignClient {
    /**
     * 校验用户是否具有操作应用的权限
     */
    @PostMapping("/api/log/search/index/export/app-permission/validate")
    R<Boolean> validateAppPermission(@RequestBody ValidatePermissionReq req);
}
