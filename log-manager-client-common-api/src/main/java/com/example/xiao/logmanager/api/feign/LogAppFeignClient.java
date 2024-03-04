package com.example.xiao.logmanager.api.feign;

import com.example.xiao.logmanager.server.common.entity.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Set;

@FeignClient(value = "log-manager-query-center")
@Component
public interface LogAppFeignClient {
    @GetMapping("/api/log/search/index/export")
    R<Map<String, Set<String>>> exportApps();
}
