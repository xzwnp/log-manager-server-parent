package com.example.xiao.logmanager.server.common.rpc;

import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.util.jwt.JwtEntity;

public interface UserInfoClient {
    R<Boolean> havePermission(RoleEnum role, String appName, String userId);
}
