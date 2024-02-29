package com.example.xiao.logmanager.server.query.center.entity.condition;

import com.example.xiao.logmanager.server.query.center.enums.LogLevelEnum;
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
public class SearchAppLogCondition {
    private List<String> indices; //索引名称
    private long current;
    private long size;
    private String keyword; //搜索关键词
    private boolean tokenizeKeyword = false; //是否对关键词进行分词
    private String negativeKeyword; //过滤关键词
    private LogLevelEnum level; //日志级别
    private LocalDateTime startTime; //开始时间
    private LocalDateTime endTime; //结束时间
    private boolean timeDesc = false; //根据时间排序是否倒序
    private String traceId;

    public long getOffset() {
        return (current - 1) * size;
    }
}
