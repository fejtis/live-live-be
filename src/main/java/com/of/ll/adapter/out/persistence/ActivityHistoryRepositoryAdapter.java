package com.of.ll.adapter.out.persistence;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.of.ll.adapter.out.persistence.mapper.ActivityHistoryMapper;
import com.of.ll.domain.history.ActivityHistoryRecord;
import com.of.ll.port.out.persistance.ActivityHistoryRepository;

@Component
public class ActivityHistoryRepositoryAdapter implements ActivityHistoryRepository {

    private final JpaActivityHistoryRepository jpa;
    private final ActivityHistoryMapper mapper;

    public ActivityHistoryRepositoryAdapter(final JpaActivityHistoryRepository jpa, final ActivityHistoryMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public void save(final ActivityHistoryRecord record) {
        jpa.save(mapper.toEntity(record));
    }

    @Override
    public List<ActivityHistoryRecord> findRecentByClientId(final String clientId, final int limit) {
        return jpa.findByClientIdOrderByGeneratedAtDesc(clientId, PageRequest.of(0, limit))
                .stream().map(mapper::toDomain)
                .toList();
    }
}
