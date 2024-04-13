package com.example.xiao.logmanager.server.statistic.entity.req;

import com.example.xiao.logmanager.server.statistic.enums.OperateStatisticDimensionEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OperateStatisticReq {
    @NotBlank
    private String appName;
    @NotBlank
    private String group;
    private List<String> operates; //操作名称(可多选)
    @NotNull
    private OperateStatisticDimensionEnum dimension; //x轴维度
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
