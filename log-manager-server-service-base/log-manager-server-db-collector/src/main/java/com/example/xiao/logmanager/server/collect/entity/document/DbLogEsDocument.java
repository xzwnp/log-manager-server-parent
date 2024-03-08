package com.example.xiao.logmanager.server.collect.entity.document;

import com.example.xiao.logmanager.server.collect.enums.RowChangeType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class DbLogEsDocument {
    private String appName;
    private String group;
    private LocalDateTime time;
    private String database;
    private String table;
    private RowChangeType changeType;
    private Map<String, String> oldColumns;
    private Map<String, String> newColumns;
    private Map<String, List<String>> diffColumns;
    @JsonProperty("@timestamp")
    private Date timestamp;
    @JsonProperty("@version")
    private Integer version = 1;
}
