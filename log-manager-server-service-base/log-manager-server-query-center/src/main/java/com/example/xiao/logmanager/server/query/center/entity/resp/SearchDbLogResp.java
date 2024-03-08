package com.example.xiao.logmanager.server.query.center.entity.resp;


import com.example.xiao.logmanager.server.query.center.entity.document.AppLogEsDocument;
import com.example.xiao.logmanager.server.query.center.enums.RowChangeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
public class SearchDbLogResp {
    private String appName;
    private String group;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime time;
    private String database;
    private String table;
    private RowChangeType changeType;
    private Map<String, String> oldColumns;
    private Map<String, String> newColumns;
    private Map<String, List<String>> diffColumns;
}
