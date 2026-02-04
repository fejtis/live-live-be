package com.of.ll.adapter.out.persistence.mapper;

import com.of.ll.adapter.out.persistence.entity.UserPreferencesEntity;
import com.of.ll.domain.preferences.UserPreferences;

public final class UserPreferencesMapper {

    public UserPreferencesEntity toEntity(final UserPreferences prefs) {
        final UserPreferencesEntity e = new UserPreferencesEntity();
        e.setPreferredStyle(prefs.preferredStyle());
        e.setClientId(prefs.clientId());
        e.setUpdatedAt(prefs.updatedAt());

        return e;
    }

    public UserPreferences toDomain(final UserPreferencesEntity e) {
        return new UserPreferences(e.clientId(), e.preferredStyle(), e.updatedAt());
    }

}
