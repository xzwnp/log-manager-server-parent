package com.example.xiao.logmanager.server.statistic.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 每日系统数据统计
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-04-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sta_system_daily")
public class StaSystemDailyPo {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 统计日期
     */
    private LocalDate statisticDate;

    /**
     * 用户数量
     */
    private Integer userNum;

    /**
     * 应用数量
     */
    private Integer appNum;

    /**
     * 日志数量
     */
    private Integer logNum;

    /**
     * 今日告警数量
     */
    private Integer alertNum;

    /**
     * 应用日志数量
     */
    private Integer appLogNum;

    /**
     * 应用日志数量
     */
    private Integer operateLogNum;

    /**
     * 数据库日志数量
     */
    private Integer dbLogNum;

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
