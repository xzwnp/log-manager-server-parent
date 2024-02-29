package com.example.xiao.logmanager.server.query.center.controller;

import com.example.xiao.log.annotation.LogRecord;
import com.example.xiao.log.annotation.LogRecordTrace;
import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.util.JsonUtil;
import com.example.xiao.logmanager.server.query.center.entity.req.SearchOperateLogReq;
import com.example.xiao.logmanager.server.query.center.entity.resp.SearchOperateLogResp;
import com.example.xiao.logmanager.server.query.center.service.OperateLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("api/log/search/operate")
@RestController
@RequiredArgsConstructor
@LogRecordTrace
@Slf4j
public class OperateLogController {
    private final OperateLogService operateLogService;

    @GetMapping("list-apps")
    @LogRecord(operate = "listApps", content = "'查询可用App'")
    public R<Map<String, Set<String>>> listApps() {
        return R.success(operateLogService.listOperateLogs());
    }

    @GetMapping("search-logs")
    public R<PageDto<SearchOperateLogResp>> searchOperateLogs(SearchOperateLogReq req) {
        log.info("searchOperateLogs,参数:{}", JsonUtil.toJson(req));
        //给时间设置默认值
        if (req.getEndTime() == null) {
            req.setEndTime(LocalDateTime.now());
        }
        if (req.getStartTime() == null) {
            req.setStartTime(req.getEndTime().minusMinutes(15));
        }
        return R.success(operateLogService.searchOperateLogs(req));
    }
}
