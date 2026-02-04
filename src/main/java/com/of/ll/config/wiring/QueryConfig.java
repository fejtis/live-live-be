package com.of.ll.config.wiring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.of.ll.application.query.GetActivityHistoryQuery;
import com.of.ll.port.out.persistance.ActivityHistoryRepository;

@Configuration
public class QueryConfig {

    @Bean
    GetActivityHistoryQuery getActivityHistoryQuery(final ActivityHistoryRepository activityHistoryRepository) {
        return new GetActivityHistoryQuery(activityHistoryRepository);
    }

}
