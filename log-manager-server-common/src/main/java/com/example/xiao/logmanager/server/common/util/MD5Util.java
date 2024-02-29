package com.example.xiao.logmanager.server.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MD5Util {

    // 生成随机盐的长度
    private static final int SALT_LENGTH = 16;
    // 加密算法
    private static final String ALGORITHM = "MD5";

    //todo salt写到配置文件
    public static final String DEFAULT_SALT = "7d584600383423c360bdf2eed1695c47";


    // 使用MD5加密算法对字符串进行加盐加密
    public static String hashWithSalt(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 使用MD5加密算法对字符串进行加盐加密
    public static String hashWithSalt(String password) {
        return hashWithSalt(password, DEFAULT_SALT);
    }

    // 生成随机盐
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // 测试
    public static void main(String[] args) {
        String password = "admin123";
        String hashedPassword = hashWithSalt(password);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
