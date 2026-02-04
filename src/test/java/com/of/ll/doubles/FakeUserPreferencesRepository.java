package com.of.ll.doubles;

import java.util.Optional;

import com.of.ll.domain.preferences.UserPreferences;
import com.of.ll.port.out.persistance.UserPreferencesRepository;

public class FakeUserPreferencesRepository implements UserPreferencesRepository {

    public Optional<UserPreferences> preferences = Optional.empty();
    public UserPreferences saved;
    public String lastClientId;

    public FakeUserPreferencesRepository withPreferences(final UserPreferences value) {
        this.preferences = Optional.ofNullable(value);
        return this;
    }

    @Override
    public Optional<UserPreferences> findByClientId(final String clientId) {
        lastClientId = clientId;
        return preferences;
    }

    @Override
    public void save(final UserPreferences preferences) {
        saved = preferences;
    }
}
