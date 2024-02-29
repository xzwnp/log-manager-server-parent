package com.example.xiao.logmanager.server.query.center.entity.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * com.example.xiao.logmanager.server.query.center.entity.cond
 *
 * @author xzwnp
 * 2024/1/26
 * 17:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchOperateLogCondition {
    private List<String> indices; //索引名称
    private long current;
    private long size;

    private String operate; //操作名称
    private String operatorId; //操作员ID
    private String operatorName; //操作员姓名

    private String traceId; //traceId
    private LocalDateTime startTime; //开始时间
    private LocalDateTime endTime; //结束时间
    private boolean timeDesc = true;

    public long getOffset() {
        return (current - 1) * size;
    }
}
