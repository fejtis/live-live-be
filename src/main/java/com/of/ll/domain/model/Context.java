package com.of.ll.domain.model;

import java.util.List;

import com.of.ll.domain.exception.DomainValidationException;

/**
 * Represents the context of a specific scenario with various attributes.
 * This is an immutable record that validates its fields during initialization.
 *
 * @param clientId
 * @param locationType           The type of location. Cannot be null.
 * @param season                 The season of the year (e.g., summer, winter). Cannot be null.
 * @param weather                The weather condition (e.g., sunny, rainy). Cannot be null.
 * @param temperatureC           The temperature in degrees Celsius. Cannot be null.
 * @param ageRange               The age range of the target audience. Cannot be null.
 * @param availableTime          The available time duration for the activity. Cannot be null.
 * @param preferredStyle         The preferred style for the context. Cannot be null.
 * @param regenerateSeed         A seed value for regenerating context-specific data.
 * @param excludeTitles          A list of titles to exclude from the context.
 * @param prefferedStyleExplicit
 */
public record Context(String clientId, LocationType locationType, Season season, Weather weather, Integer temperatureC, AgeRange ageRange,
                      Duration availableTime,
                      PreferredStyle preferredStyle, Integer regenerateSeed, List<String> excludeTitles, boolean prefferedStyleExplicit) {
    public Context {
        if (locationType == null) {
            throw new DomainValidationException("LocationType cannot be null.");
        }

        if (season == null) {
            throw new DomainValidationException("Season cannot be null.");
        }

        if (weather == null) {
            throw new DomainValidationException("Weather cannot be null.");
        }

        if (temperatureC == null) {
            throw new DomainValidationException("TemperatureC cannot be null.");
        }

        if (ageRange == null) {
            throw new DomainValidationException("AgeRange cannot be null.");
        }

        if (availableTime == null) {
            throw new DomainValidationException("AvailableTime cannot be null.");
        }

        if (preferredStyle == null) {
            throw new DomainValidationException("Preferred style cannot be null.");
        }
    }
}
