package com.example.xiao.logmanager.server.user.entity.coverter;

import com.example.xiao.logmanager.api.resp.UserInfoRpcResp;
import com.example.xiao.logmanager.server.user.entity.po.SysUserPo;
import com.example.xiao.logmanager.server.user.entity.resp.UserInfoResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SysUserConverter {

    @Mapping(target = "roles", ignore = true)
    UserInfoResp po2Resp(SysUserPo po);

    UserInfoResp po2Resp(SysUserPo po, List<String> roles);

    List<UserInfoRpcResp> po2RpcResp(List<SysUserPo> poList);

}
