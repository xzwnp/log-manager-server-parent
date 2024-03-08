package com.example.xiao.logmanager.server.query.center.entity.condition;

import com.example.xiao.logmanager.server.query.center.entity.dto.DbLogKeywordGroup;
import com.example.xiao.logmanager.server.query.center.enums.RowChangeType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SearchDbLogCondition {
    private List<String> indices;
    private long current;
    private long size;
    private String database;
    private String table;
    private List<DbLogKeywordGroup> keywordGroups;
    private RowChangeType changeType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean timeDesc = true;

    public long getOffset() {
        return (current - 1) * size;
    }

}
