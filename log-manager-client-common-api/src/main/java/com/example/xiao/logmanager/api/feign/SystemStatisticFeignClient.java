package com.example.xiao.logmanager.api.feign;

import com.example.xiao.logmanager.api.resp.SystemStatisticInfoResp;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * com.example.xiao.logmanager.api.feign
 *
 * @author xzwnp
 * 2024/4/20
 * 16:08
 */
@FeignClient(value = "SystemStatisticFeignClient",url = "http://log-manager-system-admin")
@Component
public interface SystemStatisticFeignClient {

	@GetMapping("/rpc/sys/statistic")
	R<SystemStatisticInfoResp> getSystemStatisticInfo();
}
