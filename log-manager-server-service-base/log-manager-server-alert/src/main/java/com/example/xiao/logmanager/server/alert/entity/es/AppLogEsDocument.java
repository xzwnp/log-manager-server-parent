package com.example.xiao.logmanager.server.alert.entity.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * com.example.xiao.logmanager.server.query.center.entity.document
 *
 * @author xzwnp
 * 2024/1/26
 * 19:25
 */
@Data
public class AppLogEsDocument {
    private String appName;
    private String group;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime time;
    private String level;
    private String message;
    private String traceId;
    private String thread;
    private String logger;
}
