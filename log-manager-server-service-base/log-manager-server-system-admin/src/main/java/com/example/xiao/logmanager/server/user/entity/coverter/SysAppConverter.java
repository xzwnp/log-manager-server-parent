package com.example.xiao.logmanager.server.user.entity.coverter;

import com.example.xiao.logmanager.server.user.entity.po.SysAppPo;
import com.example.xiao.logmanager.server.user.entity.resp.QueryAppResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SysAppConverter {

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "admins", ignore = true)
    QueryAppResp sysAppPo2queryAppResp(SysAppPo appPo);
}
