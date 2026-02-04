package com.of.ll.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.of.ll.adapter.out.persistence.mapper.UserPreferencesMapper;
import com.of.ll.domain.preferences.UserPreferences;
import com.of.ll.port.out.persistance.UserPreferencesRepository;

@Component
public class UserPreferencesRepositoryAdapter implements UserPreferencesRepository {

    private final JpaUserPreferencesRepository jpa;
    private final UserPreferencesMapper mapper;

    public UserPreferencesRepositoryAdapter(final JpaUserPreferencesRepository jpa, final UserPreferencesMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Optional<UserPreferences> findByClientId(final String clientId) {
        return jpa.findById(clientId).map(mapper::toDomain);
    }

    @Override
    public void save(final UserPreferences preferences) {
        jpa.save(mapper.toEntity(preferences));
    }
}
