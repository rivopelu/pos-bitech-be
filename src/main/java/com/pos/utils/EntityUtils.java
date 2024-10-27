package com.pos.utils;


import com.pos.entities.BaseEntity;

import java.util.Date;
import java.util.UUID;

public class EntityUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static <T extends BaseEntity> void created(T entity, String userId) {
        if (entity.getId() == null || entity.getId().isBlank())
            entity.setId(getUUID());
        if (entity.getCreatedBy() == null || entity.getCreatedBy().isBlank())
            entity.setCreatedBy(userId);
        if (entity.getCreatedDate() == null)
            entity.setCreatedDate(new Date().getTime());
    }


    public static <T extends BaseEntity> T updated(T entity, String userId) {
        entity.setModifiedBy(userId);
        entity.setModifiedDate(new Date().getTime());
        return entity;
    }
}

