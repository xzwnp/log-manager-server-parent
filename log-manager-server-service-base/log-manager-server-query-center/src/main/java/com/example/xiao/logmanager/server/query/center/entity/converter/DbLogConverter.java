
package com.example.xiao.logmanager.server.query.center.entity.converter;

import com.example.xiao.logmanager.server.query.center.entity.condition.SearchDbLogCondition;
import com.example.xiao.logmanager.server.query.center.entity.document.DbLogEsDocument;
import com.example.xiao.logmanager.server.query.center.entity.req.SearchDbLogReq;
import com.example.xiao.logmanager.server.query.center.entity.resp.SearchDbLogResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * DbLog相关pojo互转
 */
@Mapper(componentModel = "spring")
public interface DbLogConverter {
    //变量不一致时可以使用@Mapping注解
    @Mapping(target = "indices", ignore = true)
    SearchDbLogCondition searchReq2Condition(SearchDbLogReq req);

    SearchDbLogResp esDocument2Resp(DbLogEsDocument document);
}
