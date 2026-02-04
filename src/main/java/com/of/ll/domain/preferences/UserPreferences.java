package com.of.ll.domain.preferences;

import java.time.Instant;

import com.of.ll.domain.exception.DomainValidationException;
import com.of.ll.domain.model.PreferredStyle;

public record UserPreferences(
        String clientId,
        PreferredStyle preferredStyle,
        Instant updatedAt
) {
    public UserPreferences {
        if (clientId == null || clientId.isBlank()) {
            throw new DomainValidationException("clientId is required");
        }
        if (preferredStyle == null) {
            throw new DomainValidationException("preferredStyle is required");
        }
        if (updatedAt == null) {
            throw new DomainValidationException("updatedAt is required");
        }
    }
}
