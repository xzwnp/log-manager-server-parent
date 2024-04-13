package com.example.xiao.logmanager.api.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoRpcResp {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;


    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}
