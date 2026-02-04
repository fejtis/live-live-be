package com.of.ll.adapter.in.rest;

import java.util.List;

import com.of.ll.adapter.in.rest.dto.GenerateActivitiesRequest;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.model.Duration;
import com.of.ll.domain.model.WeatherSnapshot;
import com.of.ll.domain.time.SeasonResolver;
import com.of.ll.port.out.WeatherProvider;
import com.of.ll.port.out.persistance.ActivityHistoryRepository;

public final class ContextFactory {

    private static final int HISTORY_LIMIT = 2;
    private final WeatherProvider weatherProvider;
    private final ActivityHistoryRepository activityHistoryRepository;

    public ContextFactory(final WeatherProvider weatherProvider, final ActivityHistoryRepository activityHistoryRepository) {
        this.weatherProvider = weatherProvider;
        this.activityHistoryRepository = activityHistoryRepository;
    }

    public Context fromRequest(final GenerateActivitiesRequest request, final String clientId) {
        final WeatherSnapshot weatherSnapshot = weatherProvider.currentWeather(request.locationType());

        final List<String> excludeTitles =
                activityHistoryRepository.findRecentByClientId(clientId, HISTORY_LIMIT)
                        .stream()
                        .flatMap(r -> r.activityTitles().stream())
                        .distinct()
                        .toList();

        return new Context(clientId, request.locationType(), SeasonResolver.currentSeason(), weatherSnapshot.weather(),
                weatherSnapshot.temperatureC(), new AgeRange(request.minAge(), request.maxAge()), new Duration(request.availableMinutes()),
                request.preferredStyle(),
                request.regenerateSeed(), excludeTitles);
    }
}
