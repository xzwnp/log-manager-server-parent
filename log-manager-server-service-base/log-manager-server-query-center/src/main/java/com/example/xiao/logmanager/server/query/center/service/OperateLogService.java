package com.example.xiao.logmanager.server.query.center.service;

import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.query.center.dao.es.EsIndexDao;
import com.example.xiao.logmanager.server.query.center.dao.es.OperateLogEsDao;
import com.example.xiao.logmanager.server.query.center.entity.condition.SearchOperateLogCondition;
import com.example.xiao.logmanager.server.query.center.entity.converter.OperateLogConverter;
import com.example.xiao.logmanager.server.query.center.entity.document.OperateLogEsDocument;
import com.example.xiao.logmanager.server.query.center.entity.req.SearchOperateLogReq;
import com.example.xiao.logmanager.server.query.center.entity.resp.SearchOperateLogResp;
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
public class OperateLogService {
    private static final String LOG_TYPE = "operatelog";
    private final OperateLogEsDao operateLogDao;
    private final EsIndexDao esIndexDao;
    private final OperateLogConverter operateLogConverter;

    public Map<String, Set<String>> listOperateLogs() {
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


    public PageDto<SearchOperateLogResp> searchOperateLogs(SearchOperateLogReq req) {
        SearchOperateLogCondition condition = operateLogConverter.searchReq2Condition(req);
        String index = String.join("_", LOG_TYPE, req.getGroup(), req.getAppName(), "*");
        condition.setIndices(List.of(index));

        PageDto<OperateLogEsDocument> pageDto = operateLogDao.search(condition);
        List<SearchOperateLogResp> records = pageDto.getRecords().stream().map(operateLogConverter::esDocument2Resp).toList();
        return PageDto.<SearchOperateLogResp>builder().current(pageDto.getCurrent()).size(pageDto.getSize()).total(pageDto.getTotal()).records(records).build();
    }
}
