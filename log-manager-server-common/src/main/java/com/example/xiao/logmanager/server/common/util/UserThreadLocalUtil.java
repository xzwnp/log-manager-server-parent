package com.example.xiao.logmanager.server.common.util;

import com.example.xiao.logmanager.server.common.util.jwt.JwtEntity;

import java.util.Optional;

public class UserThreadLocalUtil {
    private static final ThreadLocal<JwtEntity> THREAD_LOCAL = new ThreadLocal<>();

    public static void setUserInfo(JwtEntity jwtEntity) {
        THREAD_LOCAL.set(jwtEntity);
    }

    public static void removeUserInfo() {
        THREAD_LOCAL.remove();
    }

    public static JwtEntity getUserInfo() {
        return THREAD_LOCAL.get();
    }

    public static Long getUserId() {
        return Optional.ofNullable(getUserInfo()).map(JwtEntity::getUserId).orElse(null);
    }

    public static String getUserName() {
        return Optional.ofNullable(getUserInfo()).map(JwtEntity::getUsername).orElse(null);
    }
}
