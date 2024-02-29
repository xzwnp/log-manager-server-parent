package com.example.xiao.logmanager.server.user.entity.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginReq {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
