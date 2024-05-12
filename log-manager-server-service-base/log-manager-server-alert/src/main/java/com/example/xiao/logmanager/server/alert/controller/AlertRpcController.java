package com.example.xiao.logmanager.server.alert.controller;

import com.example.xiao.logmanager.api.feign.AlertStatisticFeignClient;
import com.example.xiao.logmanager.server.alert.entity.po.AlertHistoryPo;
import com.example.xiao.logmanager.server.alert.service.AlertHistoryService;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * com.example.xiao.logmanager.server.alert.controller
 *
 * @author xzwnp
 * 2024/4/20
 * 16:18
 */
@RestController
@RequestMapping("rpc/alert")
@RequiredArgsConstructor
public class AlertRpcController{
	private final AlertHistoryService alertHistoryService;

	@GetMapping("count")
	public R<Integer> getAlertCountOfDay(LocalDate date) {
		Long alertCount = alertHistoryService.lambdaQuery()
			.ge(AlertHistoryPo::getCreatedTime, LocalDate.now())
			.lt(AlertHistoryPo::getCreatedTime, LocalDate.now().plusDays(1))
			.count();
		return R.success(alertCount.intValue());
	}
}
