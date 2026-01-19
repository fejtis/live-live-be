package com.of.ll.domain.model;

import com.of.ll.domain.exception.DomainValidationException;

public record Context(LocationType locationType, Season season, Weather weather, Integer temperatureC, AgeRange ageRange, Duration availableTime,
                      PreferredStyle preferredStyle) {
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
