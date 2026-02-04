package com.of.ll.application.query;

import java.util.List;

import com.of.ll.domain.history.ActivityHistoryRecord;
import com.of.ll.port.out.persistance.ActivityHistoryRepository;

public class GetActivityHistoryQuery {

    private final ActivityHistoryRepository activityHistoryRepository;

    public GetActivityHistoryQuery(final ActivityHistoryRepository activityHistoryRepository) {
        this.activityHistoryRepository = activityHistoryRepository;
    }

    public List<ActivityHistoryRecord> get(final String clientId, final int limit) {
        return activityHistoryRepository.findRecentByClientId(clientId, limit);
    }
}
