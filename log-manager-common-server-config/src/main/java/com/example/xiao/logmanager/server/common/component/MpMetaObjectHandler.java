package com.example.xiao.logmanager.server.common.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.xiao.logmanager.server.common.util.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@ConditionalOnClass(MetaObjectHandler.class)
public class MpMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updatedTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "creatorId", UserThreadLocalUtil::getUserId, Long.class);
        this.strictInsertFill(metaObject, "updaterId", UserThreadLocalUtil::getUserId, Long.class);
        this.strictInsertFill(metaObject, "deleteFlag", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updatedTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updaterId", UserThreadLocalUtil::getUserId, Long.class);
    }
}