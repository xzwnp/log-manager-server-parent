package com.example.xiao.logmanager.server.statistic.entity.resp;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OperateStatisticResp {
    private String legend; //图例名称
    @JsonProperty("xAxis")
    private List<String> xAxis; //x轴数值
    private List<Long> counts; //对应y值
}
