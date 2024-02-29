package com.example.xiao.logmanager.server.query.center.controller;

import com.example.xiao.log.annotation.LogRecord;
import com.example.xiao.log.annotation.LogRecordTrace;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.common.util.JsonUtil;
import com.example.xiao.logmanager.server.query.center.entity.req.SearchAppLogReq;
import com.example.xiao.logmanager.server.query.center.entity.resp.SearchAppLogResp;
import com.example.xiao.logmanager.server.query.center.service.AppLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * com.example.xiao.logmanager.server.query.center.controller
 *
 * @author xzwnp
 * 2024/1/25
 * 22:12
 */
@RequestMapping("api/log/search/app")
@RestController
@RequiredArgsConstructor
@LogRecordTrace
@Slf4j
public class AppLogController {
    private final AppLogService appLogService;

    @GetMapping("list-apps")
    @LogRecord(operate = "listApps", content = "'查询可用App'")
    public R<Map<String, Set<String>>> listApps() {
        return R.success(appLogService.listAppLogs());
    }

    @GetMapping("search-logs")
    @LogRecord(operate = "searchAppLogs", content = "'查询'+#req.appName+'应用日志,参数:'+#req", recordReturnValue = false)
    public R<PageDto<SearchAppLogResp>> searchAppLogs(@Validated SearchAppLogReq req) {
        log.info("searchAppLogs,参数:{}", JsonUtil.toJson(req));
        //给时间设置默认值
        if (req.getEndTime() == null) {
            req.setEndTime(LocalDateTime.now());
        }
        if (req.getStartTime() == null) {
            req.setStartTime(req.getEndTime().minusMinutes(15));
        }
        return R.success(appLogService.searchAppLogs(req));
    }
}
