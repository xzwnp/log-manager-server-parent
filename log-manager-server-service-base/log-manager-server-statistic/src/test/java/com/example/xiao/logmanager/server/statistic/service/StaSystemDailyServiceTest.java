package com.example.xiao.logmanager.server.statistic.service;

import cn.hutool.core.util.RandomUtil;
import com.example.xiao.logmanager.server.statistic.entity.po.StaSystemDailyPo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * com.example.xiao.logmanager.server.statistic.service
 *
 * @author xzwnp
 * 2024/4/20
 * 15:25
 */
@SpringBootTest
public class StaSystemDailyServiceTest {
	@Autowired
	StaSystemDailyService staSystemDailyService;

	@Test
	public void saveTest() {
		LocalDate now = LocalDate.now();
		for (int i = 0; i < 30; i++) {
			LocalDate date = now.minusDays(i);
			StaSystemDailyPo po = new StaSystemDailyPo();
			po.setStatisticDate(date);
			po.setAppNum(RandomUtil.randomInt(10, 30));
			po.setUserNum(RandomUtil.randomInt(15, 50));
			po.setAlertNum(RandomUtil.randomInt(5, 100));
			po.setAppLogNum(RandomUtil.randomInt(30000, 100000));
			po.setOperateLogNum(RandomUtil.randomInt(100, 1000));
			po.setDbLogNum(RandomUtil.randomInt(5000, 15000));
			staSystemDailyService.save(po);
		}
	}

}