package com.of.ll.port.out.persistance;

import java.util.List;

import com.of.ll.domain.history.ActivityHistoryRecord;

public interface ActivityHistoryRepository {

    void save(ActivityHistoryRecord record);

    List<ActivityHistoryRecord> findRecentByClientId(String clientId, int limit);

}
