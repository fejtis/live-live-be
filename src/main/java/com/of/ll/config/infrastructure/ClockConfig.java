package com.of.ll.config.infrastructure;

import java.time.Instant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.of.ll.port.out.Clock;

@Configuration
public class ClockConfig {

    @Bean
    Clock clock() {
        return Instant::now;
    }

}
