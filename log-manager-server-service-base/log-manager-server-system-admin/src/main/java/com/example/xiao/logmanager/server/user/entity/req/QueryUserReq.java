package com.example.xiao.logmanager.server.user.entity.req;

import com.example.xiao.logmanager.server.common.entity.req.PageRequest;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import lombok.Data;

@Data
public class QueryUserReq extends PageRequest {
    private String username;
    private RoleEnum role;
}
