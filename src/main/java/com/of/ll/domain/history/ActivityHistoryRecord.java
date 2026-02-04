package com.of.ll.domain.history;

import java.time.Instant;
import java.util.List;

import com.of.ll.domain.exception.DomainValidationException;

public record ActivityHistoryRecord(String clientId, Instant generatedAt, List<String> activityTitles, boolean fallbackUsed) {

    public ActivityHistoryRecord {
        if (clientId == null || clientId.isBlank()) {
            throw new DomainValidationException("clientId is required");
        }

        if (generatedAt == null) {
            throw new DomainValidationException("generatedAt is required");
        }

        if (activityTitles == null || activityTitles.isEmpty()) {
            throw new DomainValidationException("activityTitles must not be null or empty");
        }
    }

}
