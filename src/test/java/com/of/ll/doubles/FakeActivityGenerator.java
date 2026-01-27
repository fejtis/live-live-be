package com.of.ll.doubles;

import java.util.List;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.model.Duration;
import com.of.ll.port.out.ActivityGeneratorPort;

/**
 * Fake implementation of ActivityGeneratorPort for testing purposes.
 *
 * <p>This test double generates a predefined list of activities and can be configured
 * to return empty lists for testing fallback behavior.
 */
public class FakeActivityGenerator implements ActivityGeneratorPort {

    private final List<Activity> activitiesToReturn;

    /**
     * Constructs a FakeActivityGenerator with a predefined list of activities.
     *
     * @param activitiesToReturn the activities to return when generate() is called
     */
    public FakeActivityGenerator(final List<Activity> activitiesToReturn) {
        this.activitiesToReturn = activitiesToReturn;
    }

    /**
     * Constructs a FakeActivityGenerator that returns an empty list.
     * Useful for testing fallback behavior.
     */
    public FakeActivityGenerator() {
        this.activitiesToReturn = List.of();
    }

    /**
     * Returns the predefined list of activities.
     *
     * <p>The context parameter is ignored, making this useful for testing
     * the use case logic independently of context-based generation.
     *
     * @param context the context (ignored by this fake implementation)
     * @return the predefined list of activities
     */
    @Override
    public List<Activity> generate(final Context context) {
        return activitiesToReturn;
    }

    /**
     * Creates a FakeActivityGenerator with sample test activities.
     *
     * @return a FakeActivityGenerator containing 3 sample activities
     */
    public static FakeActivityGenerator withSampleActivities() {
        final List<Activity> sampleActivities = List.of(
                new Activity("Sample Activity 1", ActivityType.OUTDOOR, new AgeRange(5, 10), new Duration(30),
                        "Why", "Description", List.of("Step 1"), List.of("Material 1"), "Safety"),
                new Activity("Sample Activity 2", ActivityType.DIY, new AgeRange(3, 8), new Duration(45),
                        "Why", "Description", List.of("Step 1", "Step 2"), List.of("Material 1"), "Safety"),
                new Activity("Sample Activity 3", ActivityType.TRIP, new AgeRange(6, 12), new Duration(60),
                        "Why", "Description", List.of("Step 1", "Step 2", "Step 3"), List.of("Material 1"), "Safety")
        );
        return new FakeActivityGenerator(sampleActivities);
    }

    /**
     * Creates a FakeActivityGenerator with sample test invalid activities.
     *
     * @return a FakeActivityGenerator containing 3 sample activities
     */
    public static FakeActivityGenerator withSampleInvalidActivities() {
        final List<Activity> sampleActivities = List.of(
                new Activity("Sample Activity 1", ActivityType.OUTDOOR, new AgeRange(2, 2), new Duration(30),
                        "Why", "Description", List.of("Step 1"), List.of("Material 1"), "Safety"),
                new Activity("Sample Activity 2", ActivityType.DIY, new AgeRange(2, 2), new Duration(45),
                        "Why", "Description", List.of("Step 1", "Step 2"), List.of("Material 1"), "Safety"),
                new Activity("Sample Activity 3", ActivityType.TRIP, new AgeRange(2, 2), new Duration(60),
                        "Why", "Description", List.of("Step 1", "Step 2", "Step 3"), List.of("Material 1"), "Safety")
        );
        return new FakeActivityGenerator(sampleActivities);
    }

    /**
     * Creates a FakeActivityGenerator that returns no activities.
     *
     * @return a FakeActivityGenerator that always returns an empty list
     */
    public static FakeActivityGenerator empty() {
        return new FakeActivityGenerator();
    }
}
