package com.example.xiao.logmanager.server.messaging.entity.po;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_station_message")
public class StationMessagePo {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String username;

    /**
     * 消息类型 1:通知 2:消息 3:代办
     */
    private Integer messageType = 1;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否已读
     */
    private Boolean readFlag;

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
