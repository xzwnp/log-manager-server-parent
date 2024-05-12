package com.example.xiao.logmanager.server.statistic.task;

import com.example.xiao.logmanager.api.feign.AlertStatisticFeignClient;
import com.example.xiao.logmanager.api.feign.SystemStatisticFeignClient;
import com.example.xiao.logmanager.api.resp.SystemStatisticInfoResp;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.statistic.entity.po.StaSystemDailyPo;
import com.example.xiao.logmanager.server.statistic.service.LogStatisticService;
import com.example.xiao.logmanager.server.statistic.service.StaSystemDailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * com.example.xiao.logmanager.server.statistic.task
 *
 * @author xzwnp
 * 2024/4/20
 * 16:24
 */
@Component
@EnableScheduling
@RequiredArgsConstructor
public class SystemStatisticScheduleTask {
	private final SystemStatisticFeignClient systemStatisticFeignClient;
	private final AlertStatisticFeignClient alertStatisticFeignClient;
	private final StaSystemDailyService staSystemDailyService;
	private final LogStatisticService logStatisticService;

	//每五分钟执行一次
	@Scheduled(fixedRate = 5 * 60 * 1000)
	public void executeSystemStatisticTask() {
		LocalDate now = LocalDate.now();
		StaSystemDailyPo po = staSystemDailyService.lambdaQuery().eq(StaSystemDailyPo::getStatisticDate, now).one();
		//如果无今日统计数据,说明是今天第一次执行统计,创建之
		if (po == null) {
			po = new StaSystemDailyPo();
			po.setStatisticDate(now);
		}
		R<SystemStatisticInfoResp> systemStatisticResp = systemStatisticFeignClient.getSystemStatisticInfo();
		Integer appNum = systemStatisticResp.getData().getAppNum();
		Integer userNum = systemStatisticResp.getData().getUserNum();
		R<Integer> alertCountResp = alertStatisticFeignClient.getAlertCountOfDay(now);
		Integer alertNum = alertCountResp.getData();
		po.setAppNum(appNum);
		po.setUserNum(userNum);
		po.setAlertNum(alertNum);

		//查询三大日志数量
		List<Integer> logCounts = logStatisticService.countLogFromEs(now);
		Integer appLogCount = logCounts.get(0);
		Integer operateLogCount = logCounts.get(1);
		Integer dbLogCount = logCounts.get(2);
		Integer totalLogCount = appLogCount + operateLogCount + dbLogCount;
		po.setAppLogNum(appLogCount);
		po.setDbLogNum(dbLogCount);
		po.setOperateLogNum(operateLogCount);
		po.setLogNum(totalLogCount);

		//入库
		staSystemDailyService.saveOrUpdate(po);
	}
}
