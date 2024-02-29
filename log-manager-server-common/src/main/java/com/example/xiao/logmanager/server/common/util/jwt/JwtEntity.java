package com.example.xiao.logmanager.server.common.util.jwt;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * com.example.shirojwtdemo.util
 *
 * @author xiaozhiwei
 * 2022/11/28
 * 15:38
 */
@Data
@Accessors(chain = true)
public class JwtEntity {
    private Long userId;
    private String username;
    private List<String> roles;

    private String nickname;


    public JwtEntity() {
    }
}
