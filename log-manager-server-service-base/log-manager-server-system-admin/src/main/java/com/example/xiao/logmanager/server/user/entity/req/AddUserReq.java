package com.example.xiao.logmanager.server.user.entity.req;

import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import lombok.Data;

import java.util.List;

@Data
public class AddUserReq {
    public final static String defaultPassword = "admin123";
    private String username;
    private String nickname;
    private List<RoleEnum> roles;
}
