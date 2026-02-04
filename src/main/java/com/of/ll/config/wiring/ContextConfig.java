package com.of.ll.config.wiring;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.of.ll.adapter.in.rest.ContextFactory;
import com.of.ll.port.out.WeatherProvider;
import com.of.ll.port.out.persistance.ActivityHistoryRepository;
import com.of.ll.port.out.persistance.UserPreferencesRepository;

@Configuration
public class ContextConfig {

    @Bean
    ContextFactory contextFactory(@Qualifier("fixedWeatherProvider") final WeatherProvider weatherProvider,
            final ActivityHistoryRepository activityHistoryRepository, final UserPreferencesRepository userPreferencesRepository) {
        return new ContextFactory(weatherProvider, activityHistoryRepository, userPreferencesRepository);
    }

}
