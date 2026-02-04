package com.of.ll.domain.filter;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.model.Duration;
import com.of.ll.domain.model.LocationType;
import com.of.ll.domain.model.PreferredStyle;
import com.of.ll.domain.model.Season;
import com.of.ll.domain.model.Weather;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("MagicNumber")
class TimeFilterTest {

    @Nested
    class Allows {

        @Test
        void returnsTrueWhenActivityDurationIsLessThanAvailableTime() {
            final Activity activity = createActivity(new Duration(30));
            final Context context = createContext(new Duration(60));
            final TimeFilter filter = new TimeFilter(10);
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueWhenActivityDurationEqualsAvailableTime() {
            final Activity activity = createActivity(new Duration(60));
            final Context context = createContext(new Duration(60));
            final TimeFilter filter = new TimeFilter(10);
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsFalseWhenActivityDurationExceedsAvailableTime() {
            final Activity activity = createActivity(new Duration(90));
            final Context context = createContext(new Duration(60));
            final TimeFilter filter = new TimeFilter(10);
            assertFalse(filter.allows(activity, context));
        }

        @Test
        void returnsTrueWhenActivityDurationExceedsAvailableTimeByOne() {
            final Activity activity = createActivity(new Duration(61));
            final Context context = createContext(new Duration(60));
            final TimeFilter filter = new TimeFilter(10);
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueWhenBothDurationsAreZero() {
            final Activity activity = createActivity(new Duration(0));
            final Context context = createContext(new Duration(0));
            final TimeFilter filter = new TimeFilter(10);
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueWhenActivityDurationIsZero() {
            final Activity activity = createActivity(new Duration(0));
            final Context context = createContext(new Duration(120));
            final TimeFilter filter = new TimeFilter(10);
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsFalseWhenAvailableTimeIsZeroAndActivityHasDuration() {
            final Activity activity = createActivity(new Duration(30));
            final Context context = createContext(new Duration(0));
            final TimeFilter filter = new TimeFilter(10);
            assertFalse(filter.allows(activity, context));
        }

        @Test
        void returnsTrueWhenBothDurationsAreAtMaximum() {
            final Activity activity = createActivity(new Duration(Duration.MAX_DURATION));
            final Context context = createContext(new Duration(Duration.MAX_DURATION));
            final TimeFilter filter = new TimeFilter(10);
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueWhenActivityIsShortAndAvailableTimeIsMaximum() {
            final Activity activity = createActivity(new Duration(15));
            final Context context = createContext(new Duration(Duration.MAX_DURATION));
            final TimeFilter filter = new TimeFilter(10);
            assertTrue(filter.allows(activity, context));
        }
    }

    private Activity createActivity(final Duration duration) {
        return new Activity("Test Activity", ActivityType.OUTDOOR, new AgeRange(5, 10), duration,
                "Why today", "Description", List.of("Step 1"), List.of("Material 1"), "Safety notes");
    }

    private Context createContext(final Duration availableTime) {
        return new Context(UUID.randomUUID().toString(), LocationType.CITY, Season.SUMMER, Weather.SUN, 20,
                new AgeRange(3, 12), availableTime, PreferredStyle.OUTDOOR, null, List.of(), false);
    }
}
