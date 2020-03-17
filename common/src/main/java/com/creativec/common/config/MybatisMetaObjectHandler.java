package com.creativec.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.creativec.common.base.AuthDataHolder;
import com.creativec.common.tools.DateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Optional;

@Slf4j
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("start mybatis plus insert fill ....");
        AuthDataHolder.User user = AuthDataHolder.get();
        this.strictInsertFill(metaObject, "createdBy", String.class, Optional.ofNullable(user.getKid()).orElse("system"));
        this.strictInsertFill(metaObject, "createdAt", Integer.class, DateTime.getUnixStamp());
        this.strictInsertFill(metaObject, "createdFrom", String.class, "system");
        this.strictInsertFill(metaObject, "createdIp", String.class, user.getIp());
        this.strictInsertFill(metaObject, "version", Integer.class, 1);
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("start mybatis plus update fill ....");
        AuthDataHolder.User user = AuthDataHolder.get();
        this.strictUpdateFill(metaObject, "updatedBy", String.class, Optional.ofNullable(user.getKid()).orElse("system"));
        this.strictUpdateFill(metaObject, "updatedAt", Integer.class, DateTime.getUnixStamp());
        this.strictUpdateFill(metaObject, "updatedFrom", String.class, "system");
        this.strictUpdateFill(metaObject, "updatedIp", String.class, user.getIp());
    }
}
