package com.of.ll.adapter.out.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.of.ll.adapter.out.persistence.entity.ActivityHistoryEntity;

public interface JpaActivityHistoryRepository extends JpaRepository<ActivityHistoryEntity, Long> {

    List<ActivityHistoryEntity> findByClientIdOrderByGeneratedAtDesc(String clientId, Pageable pageable);

}
