package com.example.xiao.logmanager.server.user.entity.req;

import lombok.Data;

@Data
public class ChangePasswordReq {
    private String originalPassword;
    private String newPassword;
}
