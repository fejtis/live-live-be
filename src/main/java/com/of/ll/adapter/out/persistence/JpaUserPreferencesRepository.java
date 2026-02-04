package com.of.ll.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.of.ll.adapter.out.persistence.entity.UserPreferencesEntity;

public interface JpaUserPreferencesRepository extends JpaRepository<UserPreferencesEntity, String> {
}
