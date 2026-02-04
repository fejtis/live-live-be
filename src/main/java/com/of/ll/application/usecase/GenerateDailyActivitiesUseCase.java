package com.of.ll.application.usecase;

import java.util.List;
import java.util.UUID;

import com.of.ll.domain.event.ActivitiesGeneratedEvent;
import com.of.ll.domain.filter.FilterPipeline;
import com.of.ll.domain.history.ActivityHistoryRecord;
import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.selector.TopActivitiesSelector;
import com.of.ll.port.out.ActivityGeneratorPort;
import com.of.ll.port.out.Clock;
import com.of.ll.port.out.TelemetryPort;
import com.of.ll.port.out.persistance.ActivityHistoryRepository;

public class GenerateDailyActivitiesUseCase {

    private final ActivityGeneratorPort primaryGenerator;
    private final ActivityGeneratorPort fallbackGenerator;
    private final FilterPipeline filterPipeline;
    private final TopActivitiesSelector topActivitiesSelector;
    private final TelemetryPort telemetryPort;
    private final Clock clock;
    private final ActivityHistoryRepository activityHistoryRepository;

    public GenerateDailyActivitiesUseCase(final ActivityGeneratorPort primaryGenerator, final ActivityGeneratorPort fallbackGenerator,
            final FilterPipeline filterPipeline,
            final TopActivitiesSelector topActivitiesSelector,
            final TelemetryPort telemetryPort, final Clock clock, final ActivityHistoryRepository activityHistoryRepository) {
        this.primaryGenerator = primaryGenerator;
        this.fallbackGenerator = fallbackGenerator;
        this.filterPipeline = filterPipeline;
        this.topActivitiesSelector = topActivitiesSelector;
        this.telemetryPort = telemetryPort;
        this.clock = clock;
        this.activityHistoryRepository = activityHistoryRepository;
    }

    public List<Activity> generate(final Context context) {
        boolean fallbackUsed = false;
        List<Activity> filtered;
        List<Activity> generated = primaryGenerator.generate(context);
        filtered = filterPipeline.filterActivities(generated, context);

        if (filtered.isEmpty()) {
            fallbackUsed = true;
            generated = fallbackGenerator.generate(context);
            filtered = filterPipeline.filterActivities(generated, context);
        }

        final List<Activity> top = topActivitiesSelector.selectTop(context, filtered);

        final ActivitiesGeneratedEvent event = new ActivitiesGeneratedEvent(generated.size(), filtered.size(), top.size(), fallbackUsed, context.weather(),
                context.ageRange(), context.availableTime(), clock.now());
        telemetryPort.publish(event);

        activityHistoryRepository.save(
                new ActivityHistoryRecord(UUID.randomUUID().toString(), clock.now(), top.stream().map(Activity::title).toList(), fallbackUsed));

        return top;
    }
}
