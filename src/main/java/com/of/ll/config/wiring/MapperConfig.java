package com.of.ll.config.wiring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.of.ll.adapter.in.rest.mapper.ActivityHistoryResponseMapper;
import com.of.ll.adapter.in.rest.mapper.ActivityResponseMapper;
import com.of.ll.adapter.out.ai.mapper.AiActivityMapper;
import com.of.ll.adapter.out.persistence.mapper.ActivityHistoryMapper;

@Configuration
public class MapperConfig {

    @Bean
    ActivityResponseMapper activityResponseMapper() {
        return new ActivityResponseMapper();
    }

    @Bean
    AiActivityMapper aiActivityMapper(final ObjectMapper mapper) {
        return new AiActivityMapper(mapper);
    }

    @Bean
    ActivityHistoryMapper activityHistoryMapper() {
        return new ActivityHistoryMapper();
    }

    @Bean
    ActivityHistoryResponseMapper activityHistoryResponseMapper() {
        return new ActivityHistoryResponseMapper();
    }
}
