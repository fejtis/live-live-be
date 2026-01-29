package com.of.ll.config.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.of.ll.domain.filter.ActivityFilter;
import com.of.ll.domain.filter.AgeFilter;
import com.of.ll.domain.filter.FilterPipeline;
import com.of.ll.domain.filter.SafetyFilter;
import com.of.ll.domain.filter.TimeFilter;
import com.of.ll.domain.filter.WeatherFilter;
import com.of.ll.domain.scoring.ActivityTypeScoring;
import com.of.ll.domain.scoring.AgeFitScoring;
import com.of.ll.domain.scoring.DefaultScoringPolicy;
import com.of.ll.domain.scoring.ScoringPolicy;
import com.of.ll.domain.scoring.StepsScoring;
import com.of.ll.domain.scoring.TimeScoring;
import com.of.ll.domain.selector.TopActivitiesSelector;

@Configuration
public class DomainConfig {

    @Bean
    FilterPipeline filterPipeline() {
        final List<ActivityFilter> filters = new ArrayList<>();
        filters.add(new AgeFilter());
        filters.add(new SafetyFilter());
        filters.add(new TimeFilter(10));
        filters.add(new WeatherFilter());

        return new FilterPipeline(filters);
    }

    @Bean
    ScoringPolicy scoringPolicy() {
        return new DefaultScoringPolicy(List.of(new ActivityTypeScoring(), new StepsScoring(), new TimeScoring(10), new AgeFitScoring()));
    }

    @Bean
    TopActivitiesSelector topActivitiesSelector(final ScoringPolicy scoringPolicy) {
        return new TopActivitiesSelector(scoringPolicy);
    }

}
