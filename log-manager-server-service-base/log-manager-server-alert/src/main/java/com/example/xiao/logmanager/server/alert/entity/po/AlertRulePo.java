package com.example.xiao.logmanager.server.alert.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.example.xiao.logmanager.api.enums.NotificationTypeEnum;
import com.example.xiao.logmanager.server.alert.entity.dto.AlertCondition;
import com.example.xiao.logmanager.server.alert.enums.AlertLevelEnum;
import com.example.xiao.logmanager.server.alert.enums.AlertStatisticTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-03-10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "alert_rule", autoResultMap = true)
public class AlertRulePo {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属应用名称
     */
    private String appName;

    /**
     * 所属应用分组
     */
    @TableField("app_group")
    private String appGroup;

    /**
     * 规则名称
     */
    @TableField("`name`")
    private String ruleName;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 告警级别
     */
    @TableField("`level`")
    private AlertLevelEnum level;

    /**
     * 应用描述
     */
    @TableField("`description`")
    private String description;

    /**
     * 匹配条件
     */
    private String matchCondition;

    /**
     * 统计策略 1立即告警 2固定窗口计数 3:滑动窗口计数 4:固定窗口百分比
     */
    private AlertStatisticTypeEnum statisticType;

    /**
     * 告警条件,包括时间、阈值等
     */
    private String alertCondition;

    /**
     * 告警条件,包括时间、阈值等
     */
    @TableField(exist = false)
    private AlertCondition alertConditionObj;

    /**
     * 告警接收人
     */
    private String alertReceiver;

    /**
     * 通知方式
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Integer> notificationTypes;

    /**
     * 静默恢复时间
     */
    private LocalDateTime muteRevertTime;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creatorId;

    /**
     * 最后更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updaterId;

    /**
     * 是否删除(0:否 1:是)
     */
    @TableLogic
    private Boolean deleteFlag;
}
