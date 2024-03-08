package com.example.xiao.logmanager.server.query.center.service;

import com.example.xiao.logmanager.server.common.entity.resp.PageDto;
import com.example.xiao.logmanager.server.query.center.dao.es.DbLogEsDao;
import com.example.xiao.logmanager.server.query.center.dao.es.EsIndexDao;
import com.example.xiao.logmanager.server.query.center.entity.condition.SearchDbLogCondition;
import com.example.xiao.logmanager.server.query.center.entity.converter.DbLogConverter;
import com.example.xiao.logmanager.server.query.center.entity.document.DbLogEsDocument;
import com.example.xiao.logmanager.server.query.center.entity.req.SearchDbLogReq;
import com.example.xiao.logmanager.server.query.center.entity.resp.SearchDbLogResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbLogService {
    private static final String LOG_TYPE = "dblog";
    private final DbLogEsDao dbLogEsDao;
    private final DbLogConverter dbLogConverter;

    public PageDto<SearchDbLogResp> searchDbLogs(SearchDbLogReq req) {
        String index = String.join("_", LOG_TYPE, req.getGroup(), req.getAppName(), "*");
        SearchDbLogCondition condition = dbLogConverter.searchReq2Condition(req);
        condition.setIndices(List.of(index));

        PageDto<DbLogEsDocument> pageDto = dbLogEsDao.search(condition);
        List<SearchDbLogResp> records = pageDto.getRecords().stream().map(dbLogConverter::esDocument2Resp).toList();
        return PageDto.<SearchDbLogResp>builder().current(pageDto.getCurrent()).size(pageDto.getSize()).total(pageDto.getTotal()).records(records).build();
    }
}
