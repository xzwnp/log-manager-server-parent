package com.example.xiao.logmanager.server.query.center.entity.req;

import com.example.xiao.logmanager.server.common.entity.req.PageRequest;
import com.example.xiao.logmanager.server.query.center.entity.dto.DbLogKeywordGroup;
import com.example.xiao.logmanager.server.query.center.enums.RowChangeType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchDbLogReq extends PageRequest {
    @NotBlank(message = "应用名称不能为空")
    private String appName;
    @NotBlank(message = "应用分组不能为空")
    private String group;
    private String database;
    private String table;
    @Valid
    private List<DbLogKeywordGroup> keywordGroups = new ArrayList<>();
    private RowChangeType changeType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean timeDesc = true;
}
