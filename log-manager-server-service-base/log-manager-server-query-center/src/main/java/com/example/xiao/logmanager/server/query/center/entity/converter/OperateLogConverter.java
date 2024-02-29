package com.example.xiao.logmanager.server.query.center.entity.converter;

import com.example.xiao.logmanager.server.query.center.entity.condition.SearchOperateLogCondition;
import com.example.xiao.logmanager.server.query.center.entity.document.OperateLogEsDocument;
import com.example.xiao.logmanager.server.query.center.entity.req.SearchOperateLogReq;
import com.example.xiao.logmanager.server.query.center.entity.resp.SearchOperateLogResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * OperateLog相关pojo互转
 */
@Mapper(componentModel = "spring")
public interface OperateLogConverter {

    //变量不一致时可以使用@Mapping注解
//    @Mapping(target = "indices", ignore = true)
    SearchOperateLogCondition searchReq2Condition(SearchOperateLogReq req);

    SearchOperateLogResp esDocument2Resp(OperateLogEsDocument document);
}
