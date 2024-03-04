package com.example.xiao.logmanager.server.user.entity.req;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAppInfoReq {
    @NotNull
    private Long id;
    @NotBlank
    private String appName;
    @NotBlank
    private String description;
}
