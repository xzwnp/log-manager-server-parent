package com.example.xiao.logmanager.server.statistic.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * com.example.xiao.logmanager.server.statistic.entity.resp
 *
 * @author xzwnp
 * 2024/4/20
 * 14:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomePageStatisticResp {
	private RealTimeNumDto currentNum;
	private List<WeeklyLogNumDto> weeklyLogNums;
	private List<MonthlyLogNumDto> monthlyLogNums;

	@Data

	public static class RealTimeNumDto {
		private Integer appNum;
		private Integer userNum;
		private Integer logNum;
		private Integer alertNum;
	}

	@Data
	public static class WeeklyLogNumDto {
		private LocalDate date;
		private Integer appLogNum;
		private Integer operateLogNum;
		private Integer dbLogNum;
	}

	@Data
	public static class MonthlyLogNumDto {
		private LocalDate date;
		private Integer appLogNum;
		private Integer operateLogNum;
		private Integer dbLogNum;
		private Integer alertNum;
	}
}
