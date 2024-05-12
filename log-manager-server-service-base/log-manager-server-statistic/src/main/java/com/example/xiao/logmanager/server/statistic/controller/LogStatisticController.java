package com.example.xiao.logmanager.server.statistic.controller;

import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.statistic.entity.req.OperateStatisticReq;
import com.example.xiao.logmanager.server.statistic.entity.resp.HomePageStatisticResp;
import com.example.xiao.logmanager.server.statistic.entity.resp.OperateStatisticResp;
import com.example.xiao.logmanager.server.statistic.service.LogStatisticService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/statistic")
@RequiredArgsConstructor
@Validated
public class LogStatisticController {
	private final LogStatisticService logStatisticService;

	@PostMapping("homePageData")
	public R<HomePageStatisticResp> queryStatisticDataForHomePage() {
		return R.success(logStatisticService.queryStatisticDataForHomePage());
	}

	@PostMapping("listOperates")
	public R<List<String>> listOperates(@RequestParam @NotBlank String appName, @RequestParam @NotBlank String group) {
		List<String> operates = logStatisticService.listOperates(appName, group);
		return R.success(operates);
	}

	@PostMapping("operateStatistic")
	public R<List<OperateStatisticResp>> makeOperateStatistic(@RequestBody @Validated OperateStatisticReq req) {
		LocalDateTime now = LocalDateTime.now();
		if (req.getStartTime() == null) {
			req.setStartTime(now.minusMinutes(15));
		}
		if (req.getEndTime() == null) {
			req.setEndTime(now);
		}
		List<OperateStatisticResp> resp = logStatisticService.makeOperateStatistic(req);
		return R.success(resp);
	}


}
