package com.example.xiao.logmanager.server.user.entity.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class EditUserProfileReq {
    @NotBlank
    private String nickname;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
}
