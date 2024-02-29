package com.example.xiao.logmanager.server.query.center.entity.req;

import com.example.xiao.logmanager.server.common.entity.req.PageRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchOperateLogReq extends PageRequest {
    @NotBlank
    private String appName;
    @NotBlank
    private String group;

    private String operate; //操作名称
    private String operatorId; //操作员ID
    private String operatorName; //操作员姓名

    private String traceId; //traceId
    private LocalDateTime startTime; //开始时间
    private LocalDateTime endTime; //结束时间
    private Boolean timeDesc = true;
}
