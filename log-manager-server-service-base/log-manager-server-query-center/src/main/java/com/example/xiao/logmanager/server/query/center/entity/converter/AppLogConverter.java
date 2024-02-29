package com.example.xiao.logmanager.server.query.center.entity.converter;

import com.example.xiao.logmanager.server.query.center.entity.condition.SearchAppLogCondition;
import com.example.xiao.logmanager.server.query.center.entity.document.AppLogEsDocument;
import com.example.xiao.logmanager.server.query.center.entity.req.SearchAppLogReq;
import com.example.xiao.logmanager.server.query.center.entity.resp.SearchAppLogResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * AppLog相关pojo互转
 */
@Mapper(componentModel = "spring")
public interface AppLogConverter {
    //变量不一致时可以使用@Mapping注解
//    @Mapping(target = "indices", ignore = true)
    @Mapping(target = "tokenizeKeyword", source = "isTokenizeKeyword",defaultValue = "true")
    @Mapping(target = "indices",ignore = true)
    SearchAppLogCondition searchReq2Condition(SearchAppLogReq req);

    SearchAppLogResp esDocument2Resp(AppLogEsDocument document);
}
