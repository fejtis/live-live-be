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
class AgeFilterTest {

    @Nested
    class Allows {

        @Test
        void returnsTrueWhenContextFullyCoversActivityAgeRange() {
            final Activity activity = createActivity(new AgeRange(5, 10));
            final Context context = createContext(new AgeRange(3, 12));
            final AgeFilter filter = new AgeFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueWhenContextAndActivityHaveSameAgeRange() {
            final Activity activity = createActivity(new AgeRange(5, 10));
            final Context context = createContext(new AgeRange(5, 10));
            final AgeFilter filter = new AgeFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsFalseWhenContextMinIsGreaterThanActivityMin() {
            final Activity activity = createActivity(new AgeRange(5, 10));
            final Context context = createContext(new AgeRange(6, 12));
            final AgeFilter filter = new AgeFilter();
            assertFalse(filter.allows(activity, context));
        }

        @Test
        void returnsFalseWhenContextMaxIsLessThanActivityMax() {
            final Activity activity = createActivity(new AgeRange(5, 10));
            final Context context = createContext(new AgeRange(3, 8));
            final AgeFilter filter = new AgeFilter();
            assertFalse(filter.allows(activity, context));
        }

        @Test
        void returnsFalseWhenContextIsCompletelyOutsideActivityRange() {
            final Activity activity = createActivity(new AgeRange(10, 15));
            final Context context = createContext(new AgeRange(3, 8));
            final AgeFilter filter = new AgeFilter();
            assertFalse(filter.allows(activity, context));
        }

        @Test
        void returnsFalseWhenContextPartiallyOverlapsActivityFromBelow() {
            final Activity activity = createActivity(new AgeRange(5, 12));
            final Context context = createContext(new AgeRange(3, 10));
            final AgeFilter filter = new AgeFilter();
            assertFalse(filter.allows(activity, context));
        }

        @Test
        void returnsFalseWhenContextPartiallyOverlapsActivityFromAbove() {
            final Activity activity = createActivity(new AgeRange(5, 12));
            final Context context = createContext(new AgeRange(8, 15));
            final AgeFilter filter = new AgeFilter();
            assertFalse(filter.allows(activity, context));
        }

        @Test
        void returnsTrueWhenBothRangesStartAtZero() {
            final Activity activity = createActivity(new AgeRange(0, 5));
            final Context context = createContext(new AgeRange(0, 10));
            final AgeFilter filter = new AgeFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueWhenBothRangesEndAtMaxAge() {
            final Activity activity = createActivity(new AgeRange(10, 18));
            final Context context = createContext(new AgeRange(5, 18));
            final AgeFilter filter = new AgeFilter();
            assertTrue(filter.allows(activity, context));
        }
    }

    private Activity createActivity(final AgeRange ageRange) {
        return new Activity("Test Activity", ActivityType.OUTDOOR, ageRange, new Duration(2),
                "Why today", "Description", List.of("Step 1"), List.of("Material 1"), "Safety notes");
    }

    private Context createContext(final AgeRange ageRange) {
        return new Context(UUID.randomUUID().toString(), LocationType.CITY, Season.SUMMER, Weather.SUN, 20,
                ageRange, new Duration(2), PreferredStyle.OUTDOOR, null, List.of(), false);
    }
}
