package com.example.xiao.logmanager.api.feign;

import com.example.xiao.logmanager.api.resp.SystemStatisticInfoResp;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

/**
 * com.example.xiao.logmanager.api.feign
 *
 * @author xzwnp
 * 2024/4/20
 * 16:08
 */
@FeignClient("log-manager-server-alert")
@Component
public interface AlertStatisticFeignClient {

	@GetMapping("/rpc/alert/count")
	R<Integer> getAlertCountOfDay(LocalDate date);
}
