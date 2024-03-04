package com.example.xiao.logmanager.server.common.util.jwt;

/**
 * com.example.commonutils
 *
 * @author xzwnp
 * 2022/3/23
 * 12:59
 * Steps：
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * Jwt工具类,支持双Token无感刷新
 *
 * @author xiaozhiwei
 */
@Slf4j
public class JwtUtil {
    //持续时间,单位毫秒
    public static final long ACCESS_TOKEN_EXPIRE = 60 * 60 * 1000;
    public static final long Refresh_TOKEN_EXPIRE = 7 * 24 * 60 * 60 * 1000;
    //秘钥,使用Keys.secretKeyFor(SignatureAlgorithm.HS256)生成
    public static final byte[] APP_SECRET = {-33, -17, -85, -5, 90, 103, 52, 69, -97, -68, 31, 84, -86, -101, -100, 120, -36, 117, 48, 85, 66, 102, 126, 124, 103, -36, 99, 56, -77, 92, -94, -96};
    private static final String SUBJECT = "jwt-demo";
    private static final String USER_INFO_CLAIM = "user-info";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String createAccessToken(JwtEntity jwtEntity) {
        return createJwtToken(jwtEntity, ACCESS_TOKEN_EXPIRE);
    }

    public static String createRefreshToken(JwtEntity jwtEntity) {
        return createJwtToken(jwtEntity, Refresh_TOKEN_EXPIRE);
    }

    private static String createJwtToken(JwtEntity jwtEntity, long expire) {
        String JwtToken = Jwts.builder()
                //第一部分 JWT头
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //第二部分 payload(主体),也叫claim 包括了一些基本信息和自定义信息
                //主题
                .setSubject(SUBJECT)
                //发布日期
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                //自定义信息,需要根据情况修改
                .claim(USER_INFO_CLAIM, jwtEntity)
                //设置签名(秘钥加密算法)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtUtil.TOKEN_PREFIX + JwtToken;
    }

    /**
     * 判断token是否存在与有效
     *
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isBlank(jwtToken)) {
            return false;
        }
        if (jwtToken.startsWith(TOKEN_PREFIX)) {
            jwtToken = jwtToken.substring(TOKEN_PREFIX.length());
        }
        try {
            Jwts.parserBuilder().setSigningKey(APP_SECRET).build().parseClaimsJws(jwtToken);
        } catch (Exception e) {
            log.info("token不存在,错误信息:{}", e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    public static JwtEntity getUserInfo(String jwtToken) {
        if (jwtToken.startsWith(TOKEN_PREFIX)) {
            jwtToken = jwtToken.substring(TOKEN_PREFIX.length());
        }
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(APP_SECRET).build().parseClaimsJws(jwtToken);
        Map<String, Object> map = (Map<String, Object>) claimsJws.getBody().get(USER_INFO_CLAIM);
        try {
            String s = objectMapper.writeValueAsString(map);
            return objectMapper.readValue(s, JwtEntity.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("token解析失败!");
        }
    }

}
