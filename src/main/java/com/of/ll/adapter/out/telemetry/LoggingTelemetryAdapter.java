package com.of.ll.adapter.out.telemetry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.of.ll.domain.event.ActivitiesGeneratedEvent;
import com.of.ll.domain.event.DomainEvent;
import com.of.ll.port.out.TelemetryPort;

@Component
public class LoggingTelemetryAdapter implements TelemetryPort {

    private static final Logger log = LoggerFactory.getLogger(LoggingTelemetryAdapter.class);

    @Override
    public void publish(final DomainEvent event) {
        try {
            if (event instanceof final ActivitiesGeneratedEvent e) {
                log.info(
                        "activities_generated generated={} filtered={} returned={} fallback={} weather={} ageRange={} time={}",
                        e.generatedCount(),
                        e.filteredCount(),
                        e.returnedCount(),
                        e.fallbackUsed(),
                        e.weather(),
                        e.ageRange(),
                        e.availableTime().minutes()
                );
            }
        } catch (final Exception e) {
            log.debug("Telemetry logging failed", e);
        }
    }
}
