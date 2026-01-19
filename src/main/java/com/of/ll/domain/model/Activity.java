package com.of.ll.domain.model;

import java.util.List;

import com.of.ll.domain.exception.DomainValidationException;

public record Activity(String title, ActivityType activityType, AgeRange ageRange, Duration duration, String whyToday, String description, List<String> steps,
                       List<String> materials, String safetyNotes) {
    public Activity {
        if (title.isEmpty()) {
            throw new DomainValidationException("Title cannot be blank.");
        }

        if (steps.isEmpty() || steps.size() > 3) {
            throw new DomainValidationException("Steps must contain between 1 and 3 items.");
        }
    }
}
