package com.of.ll.adapter.out.persistence.mapper;

import com.of.ll.adapter.out.persistence.entity.ActivityHistoryEntity;
import com.of.ll.domain.history.ActivityHistoryRecord;

public final class ActivityHistoryMapper {

    public ActivityHistoryEntity toEntity(final ActivityHistoryRecord record) {
        final ActivityHistoryEntity entity = new ActivityHistoryEntity();
        entity.setClientId(record.clientId());
        entity.setGeneratedAt(record.generatedAt());
        entity.setActivityTitles(record.activityTitles());
        entity.setFallbackUsed(record.fallbackUsed());

        return entity;
    }

    public ActivityHistoryRecord toDomain(final ActivityHistoryEntity e) {
        return new ActivityHistoryRecord(e.getClientId(), e.getGeneratedAt(), e.getActivityTitles(), e.isFallbackUsed());
    }
}
