package com.of.ll.port.out.persistance;

import java.util.Optional;

import com.of.ll.domain.preferences.UserPreferences;

public interface UserPreferencesRepository {

    Optional<UserPreferences> findByClientId(String clientId);

    void save(UserPreferences preferences);

}
