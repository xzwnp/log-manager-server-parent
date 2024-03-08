package com.example.xiao.logmanager.server.query.center.entity.document;

import com.example.xiao.logmanager.server.query.center.enums.RowChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
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
}
