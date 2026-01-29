package com.of.ll.config.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.of.ll.application.usecase.GenerateDailyActivitiesUseCase;
import com.of.ll.domain.filter.FilterPipeline;
import com.of.ll.domain.selector.TopActivitiesSelector;
import com.of.ll.port.out.ActivityGeneratorPort;
import com.of.ll.port.out.Clock;
import com.of.ll.port.out.TelemetryPort;

@Configuration
public class UseCaseConfig {

    @Bean
    public GenerateDailyActivitiesUseCase generateDailyActivitiesUseCase(
            @Qualifier("springAiActivityGenerator") final ActivityGeneratorPort springAiActivityGenerator,
            @Qualifier("fallbackActivityGenerator") final ActivityGeneratorPort fallbackActivityGenerator, final FilterPipeline filterPipeline,
            final TopActivitiesSelector selector, final TelemetryPort telemetryPort, final Clock clock) {
        return new GenerateDailyActivitiesUseCase(springAiActivityGenerator, fallbackActivityGenerator, filterPipeline, selector, telemetryPort, clock);

    }

}
