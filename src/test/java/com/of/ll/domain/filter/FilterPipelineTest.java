package com.of.ll.domain.filter;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.model.Duration;
import com.of.ll.domain.model.LocationType;
import com.of.ll.domain.model.PreferredStyle;
import com.of.ll.domain.model.Season;
import com.of.ll.domain.model.Weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("MagicNumber")
class FilterPipelineTest {

    private final FilterPipeline filterPipeline = new FilterPipeline(List.of(new AgeFilter(), new SafetyFilter(), new TimeFilter(10), new WeatherFilter()));

    @Nested
    class FilterActivities {

        @Test
        void returnsEmptyListWhenInputIsEmpty() {
            final List<Activity> filteredActivities = filterPipeline.filterActivities(List.of(),
                    getContext());
            assertTrue(filteredActivities.isEmpty());
        }

        @Test
        void returnsAllActivitiesWhenAllPassAllFilters() {
            final Activity activity1 = createActivity("Activity 1", ActivityType.DIY, new AgeRange(10, 18), new Duration(60), "Safe description");
            final Activity activity2 = createActivity("Activity 2", ActivityType.TRIP, new AgeRange(12, 16), new Duration(50), "Another safe description");

            final List<Activity> result = filterPipeline.filterActivities(List.of(activity1, activity2), getContext());

            assertEquals(2, result.size());
            assertTrue(result.contains(activity1));
            assertTrue(result.contains(activity2));
        }

        @Test
        void returnsEmptyListWhenNoActivityPassesFilters() {
            final Activity activityFailsSafety = createActivity("Unsafe", ActivityType.DIY, new AgeRange(10, 18), new Duration(60), forbiddenWord());
            final Activity activityFailsAge = createActivity("Wrong age", ActivityType.DIY, new AgeRange(5, 8), new Duration(60), "Safe");

            final List<Activity> result = filterPipeline.filterActivities(List.of(activityFailsSafety, activityFailsAge), getContext());

            assertTrue(result.isEmpty());
        }

        @Test
        void returnsOnlyActivitiesThatPassAllFilters() {
            final Activity passingActivity = createActivity("Passing", ActivityType.DIY, new AgeRange(10, 18), new Duration(60), "Safe description");
            final Activity failingActivity = createActivity("Failing", ActivityType.DIY, new AgeRange(5, 8), new Duration(60), "Safe description");

            final List<Activity> result = filterPipeline.filterActivities(List.of(passingActivity, failingActivity), getContext());

            assertEquals(1, result.size());
            assertTrue(result.contains(passingActivity));
        }

        @Test
        void excludesActivityThatFailsSafetyFilter() {
            final Activity unsafeActivity = createActivity("Unsafe", ActivityType.DIY, new AgeRange(10, 18), new Duration(60), "Do " + forbiddenWord());

            final List<Activity> result = filterPipeline.filterActivities(List.of(unsafeActivity), getContext());

            assertTrue(result.isEmpty());
        }

        @Test
        void excludesActivityThatFailsAgeFilter() {
            final Activity wrongAgeActivity = createActivity("Wrong age", ActivityType.DIY, new AgeRange(5, 9), new Duration(60), "Safe description");

            final List<Activity> result = filterPipeline.filterActivities(List.of(wrongAgeActivity), getContext());

            assertTrue(result.isEmpty());
        }

        @Test
        void excludesActivityThatFailsTimeFilter() {
            final Activity tooLongActivity = createActivity("Too long", ActivityType.DIY, new AgeRange(10, 18), new Duration(80), "Safe description");

            final List<Activity> result = filterPipeline.filterActivities(List.of(tooLongActivity), getContext());

            assertTrue(result.isEmpty());
        }

        @Test
        void excludesActivityThatFailsWeatherFilter() {
            final Activity longOutdoorActivity = createActivity("Long outdoor", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(40),
                    "Safe description");

            final List<Activity> result = filterPipeline.filterActivities(List.of(longOutdoorActivity), getContext());

            assertTrue(result.isEmpty());
        }

        @Test
        void excludesActivityThatFailsMultipleFilters() {
            final Activity multipleFailures = createActivity("Multiple failures", ActivityType.OUTDOOR, new AgeRange(5, 8), new Duration(50), forbiddenWord());

            final List<Activity> result = filterPipeline.filterActivities(List.of(multipleFailures), getContext());

            assertTrue(result.isEmpty());
        }

        @Test
        void preservesOrderOfPassingActivities() {
            final Activity first = createActivity("First", ActivityType.DIY, new AgeRange(10, 18), new Duration(60), "Safe");
            final Activity second = createActivity("Second", ActivityType.TRIP, new AgeRange(12, 16), new Duration(50), "Safe");
            final Activity third = createActivity("Third", ActivityType.DIY, new AgeRange(11, 17), new Duration(40), "Safe");

            final List<Activity> result = filterPipeline.filterActivities(List.of(first, second, third), getContext());

            assertEquals(3, result.size());
            assertEquals(first, result.get(0));
            assertEquals(second, result.get(1));
            assertEquals(third, result.get(2));
        }
    }

    @NonNull
    private static Context getContext() {
        return new Context(UUID.randomUUID().toString(), LocationType.CITY, Season.SUMMER, Weather.LIGHT_RAIN, 20, new AgeRange(10, 18), new Duration(60),
                PreferredStyle.MIX,
                null, List.of(), false);
    }

    @NonNull
    private static Activity createActivity(final String title, final ActivityType type, final AgeRange ageRange, final Duration duration,
            final String description) {
        return new Activity(title, type, ageRange, duration, "Why today", description, List.of("Step 1"), List.of(), "Safety notes");
    }

    private static String forbiddenWord() {
        return SafetyFilter.FORBIDDEN_WORDS.stream().findFirst().orElseThrow();
    }
}
