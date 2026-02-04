package com.of.ll.adapter.in.rest;

import java.util.List;

import com.of.ll.adapter.in.rest.dto.GenerateActivitiesRequest;
import com.of.ll.domain.exception.DomainValidationException;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.model.Duration;
import com.of.ll.domain.model.PreferredStyle;
import com.of.ll.domain.model.WeatherSnapshot;
import com.of.ll.domain.preferences.UserPreferences;
import com.of.ll.domain.time.SeasonResolver;
import com.of.ll.port.out.WeatherProvider;
import com.of.ll.port.out.persistance.ActivityHistoryRepository;
import com.of.ll.port.out.persistance.UserPreferencesRepository;

public final class ContextFactory {

    private static final int HISTORY_LIMIT = 2;
    private final WeatherProvider weatherProvider;
    private final ActivityHistoryRepository activityHistoryRepository;
    private final UserPreferencesRepository userPreferencesRepository;

    public ContextFactory(final WeatherProvider weatherProvider, final ActivityHistoryRepository activityHistoryRepository,
            final UserPreferencesRepository userPreferencesRepository) {
        this.weatherProvider = weatherProvider;
        this.activityHistoryRepository = activityHistoryRepository;
        this.userPreferencesRepository = userPreferencesRepository;
    }

    public Context fromRequest(final GenerateActivitiesRequest request, final String clientId) {
        if (clientId == null || clientId.isBlank()) {
            throw new DomainValidationException("Client ID is required");
        }

        final WeatherSnapshot weatherSnapshot = weatherProvider.currentWeather(request.locationType());

        final List<String> excludeTitles =
                activityHistoryRepository.findRecentByClientId(clientId, HISTORY_LIMIT)
                        .stream()
                        .flatMap(r -> r.activityTitles().stream())
                        .distinct()
                        .toList();

        PreferredStyle preferredStyle = request.preferredStyle();
        boolean preferredStyleExplicit = true;

        if (request.preferredStyle() == null) {
            preferredStyleExplicit = false;
            preferredStyle = userPreferencesRepository.findByClientId(clientId).map(UserPreferences::preferredStyle).orElse(PreferredStyle.MIX);
        }

        return new Context(clientId, request.locationType(), SeasonResolver.currentSeason(), weatherSnapshot.weather(),
                weatherSnapshot.temperatureC(), new AgeRange(request.minAge(), request.maxAge()), new Duration(request.availableMinutes()),
                preferredStyle,
                request.regenerateSeed(), excludeTitles, preferredStyleExplicit);
    }
}
