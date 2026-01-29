package com.of.ll.adapter.out.ai.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.of.ll.adapter.out.ai.dto.AiActivity;
import com.of.ll.adapter.out.ai.dto.AiResponse;
import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Duration;

@Component
public class AiActivityMapper {

    private final ObjectMapper mapper;

    public AiActivityMapper(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Converts JSON string to AiResponse object
     * @param json the JSON string to be parsed
     * @return AiResponse object
     * @throws Exception if parsing fails
     */
    public AiResponse parse(final String json) throws Exception {
        return mapper.readValue(json, AiResponse.class);
    }

    public Optional<Activity> toDomain(final AiActivity aiActivity) {
        return Optional.of(new Activity(
                require(aiActivity.title()),
                parseType(aiActivity.type()),
                parseAgeRange(aiActivity.age_range()),
                new Duration(require(aiActivity.duration_min())),
                require(aiActivity.why_today()),
                require(aiActivity.description()),
                requireSteps(aiActivity.steps()),
                safeList(aiActivity.materials()),
                require(aiActivity.safety_notes())
        ));
    }

    private List<String> safeList(final List<String> materials) {
        return materials == null ? List.of() : List.copyOf(materials);
    }

    private List<String> requireSteps(final List<String> steps) {
        if (steps == null || steps.isEmpty() || steps.size() > 3) {
            throw new IllegalArgumentException("Invalid steps");
        }

        return List.copyOf(steps);
    }

    private AgeRange parseAgeRange(final String ageRange) {
        final String[] ages = require(ageRange).split("-");

        if (ages.length != 2) {
            throw new IllegalArgumentException("Invalid age range format");
        }

        return new AgeRange(Integer.parseInt(ages[0]), Integer.parseInt(ages[1]));
    }

    private <T> T require(final T value) {
        if (value == null) {
            throw new IllegalArgumentException("Required value missing");
        }

        return value;
    }

    private ActivityType parseType(final String type) {
        return ActivityType.valueOf(require(type).toUpperCase());
    }

}
