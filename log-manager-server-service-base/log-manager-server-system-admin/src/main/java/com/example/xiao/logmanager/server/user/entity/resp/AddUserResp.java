package com.example.xiao.logmanager.server.user.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddUserResp {
    private String username;
    private String password;
    private List<String> roles;
}
