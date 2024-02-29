package com.example.xiao.logmanager.server.query.center.entity.resp;


import com.example.xiao.logmanager.server.query.center.entity.document.AppLogEsDocument;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * com.example.xiao.logmanager.server.query.center.entity.resp
 *
 * @author xzwnp
 * 2024/1/26
 * 14:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchAppLogResp {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime time;
    private String level;
    private String message;
    private String traceId;
    private String thread;
    private String logger;
    private String messageHighlight;

    public SearchAppLogResp(AppLogEsDocument document) {
        this.time = document.getTime();
        this.level = document.getLevel();
        this.message = document.getMessage();
        this.traceId = document.getTraceId();
        this.messageHighlight = document.getMessageHighlight();
        this.logger = document.getLogger();
        this.thread = document.getThread();
    }
}
