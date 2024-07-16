package com.rts.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: RTS
 * @CreateDateTime: 2024/7/15 17:27
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATED = "created";

    private static final String LAST_MODIFIED = "lastModified";

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充...");
        this.strictInsertFill(metaObject, CREATED, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, LAST_MODIFIED, LocalDateTime.class, LocalDateTime.now());
        log.info("结束插入填充...");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充...");
        this.strictUpdateFill(metaObject, LAST_MODIFIED, LocalDateTime.class, LocalDateTime.now());
    }
}
