package com.example.xiao.logmanager.server.alert.entity.dto;

import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import lombok.Getter;

@Getter
public abstract class AlertCondition {
    protected AlertStatisticTypeEnum type;

}
