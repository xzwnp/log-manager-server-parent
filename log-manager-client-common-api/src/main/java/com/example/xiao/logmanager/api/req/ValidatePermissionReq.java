package com.example.xiao.logmanager.api.req;

import lombok.Data;

@Data
public class ValidatePermissionReq {
    private Long userId;
    private Long appId;
    private String requiredRole;
}
