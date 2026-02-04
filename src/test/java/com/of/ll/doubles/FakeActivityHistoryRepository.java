package com.of.ll.doubles;

import java.util.ArrayList;
import java.util.List;

import com.of.ll.domain.history.ActivityHistoryRecord;
import com.of.ll.port.out.persistance.ActivityHistoryRepository;

public class FakeActivityHistoryRepository implements ActivityHistoryRepository {

    public final List<ActivityHistoryRecord> saved = new ArrayList<>();
    public List<ActivityHistoryRecord> recent = List.of();
    public String lastClientId;
    public int lastLimit;

    public FakeActivityHistoryRepository withRecent(final List<ActivityHistoryRecord> recentHistory) {
        this.recent = List.copyOf(recentHistory);
        return this;
    }

    @Override
    public void save(final ActivityHistoryRecord record) {
        saved.add(record);
    }

    @Override
    public List<ActivityHistoryRecord> findRecentByClientId(final String clientId, final int limit) {
        lastClientId = clientId;
        lastLimit = limit;
        return recent;
    }
}
