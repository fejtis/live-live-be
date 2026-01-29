package com.of.ll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.of.ll.adapter.in.rest.mapper.ActivityResponseMapper;

@Configuration
public class RestMapperConfig {

    @Bean
    ActivityResponseMapper activityResponseMapper() {
        return new ActivityResponseMapper();
    }

}
