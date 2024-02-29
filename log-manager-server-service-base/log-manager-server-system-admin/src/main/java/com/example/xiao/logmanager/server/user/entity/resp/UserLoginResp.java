package com.example.xiao.logmanager.server.user.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class UserLoginResp {
    private String username;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime expires;
}
