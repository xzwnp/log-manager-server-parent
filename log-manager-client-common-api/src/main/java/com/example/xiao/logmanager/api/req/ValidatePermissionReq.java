package com.example.xiao.logmanager.api.req;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidatePermissionReq {
    private Long userId;
    private Long appId;
    private String requiredRole;
}
