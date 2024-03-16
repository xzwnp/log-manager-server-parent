package com.example.xiao.logmanager.server.alert.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import com.example.xiao.logmanager.server.alert.enums.AlertLevelEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-03-14
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("alert_history")
public class AlertHistoryPo {

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
    private String appGroup;

    /**
     * 规则名称
     */
    private Long ruleId;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 告警级别
     */
    @TableField("`level`")
    private AlertLevelEnum level;

    /**
     * 规则描述
     */
    private String ruleDescription;

    /**
     * 告警信息描述
     */
    private String alertDescription;

    /**
     * 告警接收人
     */
    private String alertReceiver;

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
    private Byte deleteFlag;
}
