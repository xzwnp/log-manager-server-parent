package com.example.xiao.logmanager.server.user.entity.req;

import lombok.Data;

@Data
public class ValidatePermissionReq {
    private Long userId;
    private Long appId;
    private String requiredRole;
}
