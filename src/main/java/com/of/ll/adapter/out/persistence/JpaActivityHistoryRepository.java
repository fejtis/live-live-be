package com.of.ll.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.of.ll.adapter.out.persistence.entity.ActivityHistoryEntity;

public interface JpaActivityHistoryRepository extends JpaRepository<ActivityHistoryEntity, Long> {
}
