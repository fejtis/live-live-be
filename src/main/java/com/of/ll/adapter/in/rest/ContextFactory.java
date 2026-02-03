package com.of.ll.adapter.in.rest;

import com.of.ll.adapter.in.rest.dto.GenerateActivitiesRequest;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.model.Duration;
import com.of.ll.domain.model.WeatherSnapshot;
import com.of.ll.domain.time.SeasonResolver;
import com.of.ll.port.out.WeatherProvider;

public final class ContextFactory {

    private final WeatherProvider weatherProvider;

    public ContextFactory(final WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    public Context fromRequest(final GenerateActivitiesRequest request) {
        final WeatherSnapshot weatherSnapshot = weatherProvider.currentWeather(request.locationType());

        return new Context(request.locationType(), SeasonResolver.currentSeason(), weatherSnapshot.weather(), weatherSnapshot.temperatureC(),
                new AgeRange(request.minAge(), request.maxAge()), new Duration(request.availableMinutes()), request.preferredStyle(), request.regenerateSeed());
    }
}
