package com.of.ll.domain.event;

import java.time.Instant;

import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Duration;
import com.of.ll.domain.model.Weather;

public record ActivitiesGeneratedEvent(int generatedCount, int filteredCount, int returnedCount, boolean fallbackUsed, Weather weather, AgeRange ageRange,
                                       Duration availableTime, Instant occurredAt) implements DomainEvent {

}
