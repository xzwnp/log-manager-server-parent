package com.example.xiao.logmanager.server.query.center.controller;

import com.example.xiao.log.annotation.LogRecord;
import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.util.JsonUtil;
import com.example.xiao.logmanager.server.query.center.entity.req.SearchDbLogReq;
import com.example.xiao.logmanager.server.query.center.entity.resp.SearchDbLogResp;
import com.example.xiao.logmanager.server.query.center.service.DbLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/log/search/db")
@RequiredArgsConstructor
@Slf4j
public class DbLogController {
    private final DbLogService dbLogService;

    @PostMapping("search-logs")
    @LogRecord(operate = "searchDbLogs", content = "'查询'+#req.database+'数据库日志,参数:'+#req", recordReturnValue = false)
    public R<PageDto<SearchDbLogResp>> searchDbLogs(@Validated @RequestBody SearchDbLogReq req) {
        log.info("searchDbLogs,参数:{}", JsonUtil.toJson(req));
        //给时间设置默认值
        if (req.getEndTime() == null) {
            req.setEndTime(LocalDateTime.now());
        }
        if (req.getStartTime() == null) {
            req.setStartTime(req.getEndTime().minusMinutes(15));
        }
        return R.success(dbLogService.searchDbLogs(req));
    }
}
