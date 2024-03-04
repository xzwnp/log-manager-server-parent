package com.example.xiao.logmanager.server.query.center.controller;

import com.example.xiao.log.annotation.LogRecord;
import com.example.xiao.log.annotation.LogRecordTrace;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.query.center.service.AppLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RequestMapping("api/log/search/index")
@RestController
@RequiredArgsConstructor
@LogRecordTrace
@Slf4j
public class LogIndexController {
    private final AppLogService appLogService;

    @GetMapping("export")
    @LogRecord(operate = "exportEsApps", content = "'导出Es中存储的所有App'")
    public R<Map<String, Set<String>>> exportEsApps() {
        return R.success(appLogService.export());
    }
}
