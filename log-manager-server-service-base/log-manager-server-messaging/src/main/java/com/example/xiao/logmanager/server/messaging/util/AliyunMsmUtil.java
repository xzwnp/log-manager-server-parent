package com.example.xiao.logmanager.server.messaging.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * com.example.msmservice.util
 *
 * @author xzwnp
 * 2022/3/25
 * 17:39
 * Steps：
 */
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun.msm.file",ignoreUnknownFields = false)
public class AliyunMsmUtil implements InitializingBean {
    private String keyid;
    private String keysecret;
    private static String KEYID;
    private static String KEYSECRET;
    public static Client createClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(KEYID)
                // 您的AccessKey Secret
                .setAccessKeySecret(KEYSECRET);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        KEYID = keyid;
        KEYSECRET = keysecret;
    }

}
