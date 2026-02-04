package com.of.ll.config.wiring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.of.ll.adapter.out.persistence.ActivityHistoryRepositoryAdapter;
import com.of.ll.port.out.persistance.ActivityHistoryRepository;

@Configuration
public class RepositoryConfig {

    @Bean
    ActivityHistoryRepository activityHistoryRepository(final ActivityHistoryRepositoryAdapter adapter) {
        return adapter;
    }

}
