package com.example.xiao.logmanager.server.query.center.controller;

import com.example.xiao.logmanager.server.common.entity.R;
import com.example.xiao.logmanager.server.common.entity.resp.PageResponse;
import com.example.xiao.logmanager.server.query.center.entity.dto.AppLogIndexDto;
import com.example.xiao.logmanager.server.query.center.entity.req.SearchAppLogReq;
import com.example.xiao.logmanager.server.query.center.entity.resp.SearchAppLogResp;
import com.example.xiao.logmanager.server.query.center.service.AppLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.example.xiao.logmanager.server.query.center.controller
 *
 * @author xzwnp
 * 2024/1/25
 * 22:12
 */
@RequestMapping("api/logs/app")
@RestController
@RequiredArgsConstructor
public class AppLogController {
	private final AppLogService appLogService;

	@GetMapping("list-apps")
	public R<List<AppLogIndexDto>> listApps() {
		return R.success(appLogService.listAppLogs());
	}

	@GetMapping("search-logs")
	public R<PageResponse<SearchAppLogResp>> searchAppLogs(SearchAppLogReq req) {
		return R.success(appLogService.searchAppLogs(req));
	}
}
