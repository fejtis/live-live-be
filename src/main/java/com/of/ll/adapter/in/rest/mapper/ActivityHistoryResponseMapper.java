package com.of.ll.adapter.in.rest.mapper;

import com.of.ll.adapter.in.rest.dto.ActivityHistoryResponse;
import com.of.ll.domain.history.ActivityHistoryRecord;

public final class ActivityHistoryResponseMapper {

    public ActivityHistoryResponse toResponse(final ActivityHistoryRecord record) {
        return new ActivityHistoryResponse(record.generatedAt(), record.activityTitles(), record.fallbackUsed());
    }

}
