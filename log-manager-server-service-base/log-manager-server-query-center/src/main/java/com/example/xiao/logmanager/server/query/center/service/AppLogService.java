package com.example.xiao.logmanager.server.query.center.service;

import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.query.center.dao.es.AppLogEsDao;
import com.example.xiao.logmanager.server.query.center.dao.es.EsIndexDao;
import com.example.xiao.logmanager.server.query.center.entity.condition.SearchAppLogCondition;
import com.example.xiao.logmanager.server.query.center.entity.converter.AppLogConverter;
import com.example.xiao.logmanager.server.query.center.entity.document.AppLogEsDocument;
import com.example.xiao.logmanager.server.query.center.entity.req.SearchAppLogReq;
import com.example.xiao.logmanager.server.query.center.entity.resp.SearchAppLogResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * com.example.xiao.logmanager.server.query.center.service
 *
 * @author xzwnp
 * 2024/1/25
 * 22:10
 */
@Service
@RequiredArgsConstructor
public class AppLogService {
    private static final String LOG_TYPE = "applog";
    private final AppLogEsDao appLogEsDao;
    private final EsIndexDao esIndexDao;
    private final AppLogConverter appLogConverter;

    public Map<String, Set<String>> export() {
        List<String> indices = esIndexDao.listIndices(LOG_TYPE + "*");
        Map<String, Set<String>> appGroupMap = new HashMap<>();
        // 处理返回的索引信息
        for (String index : indices) {
            try {
                //%{log-type}_%{group}_%{app-name}_%{date}
                String[] split = index.split("_");
                String groupName = split[1];
                String appName = split[2];
                Set<String> groupSet = appGroupMap.computeIfAbsent(appName, k -> new HashSet<>());
                groupSet.add(groupName);
            } catch (IndexOutOfBoundsException e) {
                //索引格式不合规,跳过....
            }
        }
        return appGroupMap;
    }

    public PageDto<SearchAppLogResp> searchAppLogs(SearchAppLogReq req) {
        String index = String.join("_", LOG_TYPE, req.getGroup(), req.getAppName(), "*");
        SearchAppLogCondition condition = appLogConverter.searchReq2Condition(req);
        condition.setIndices(List.of(index));

        PageDto<AppLogEsDocument> pageDto = appLogEsDao.search(condition);
        List<SearchAppLogResp> records = pageDto.getRecords().stream().map(SearchAppLogResp::new).toList();
        return PageDto.<SearchAppLogResp>builder().current(pageDto.getCurrent()).size(pageDto.getSize()).total(pageDto.getTotal()).records(records).build();
    }
}
