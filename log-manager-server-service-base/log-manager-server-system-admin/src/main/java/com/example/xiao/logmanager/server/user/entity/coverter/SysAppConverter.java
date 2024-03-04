package com.example.xiao.logmanager.server.user.entity.coverter;

import com.example.xiao.logmanager.server.user.entity.po.SysAppPo;
import com.example.xiao.logmanager.server.user.entity.resp.QueryAppResp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysAppConverter {

    QueryAppResp sysAppPo2queryAppResp(SysAppPo appPo);
}
