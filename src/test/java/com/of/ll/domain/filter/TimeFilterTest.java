package com.of.ll.domain.filter;

import java.util.List;

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

    @Test
    void allowsReturnsTrueWhenActivityDurationIsLessThanAvailableTime() {
        final Activity activity = createActivity(new Duration(30));
        final Context context = createContext(new Duration(60));
        final TimeFilter filter = new TimeFilter(10);
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueWhenActivityDurationEqualsAvailableTime() {
        final Activity activity = createActivity(new Duration(60));
        final Context context = createContext(new Duration(60));
        final TimeFilter filter = new TimeFilter(10);
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsFalseWhenActivityDurationExceedsAvailableTime() {
        final Activity activity = createActivity(new Duration(90));
        final Context context = createContext(new Duration(60));
        final TimeFilter filter = new TimeFilter(10);
        assertFalse(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueWhenActivityDurationExceedsAvailableTimeByOne() {
        final Activity activity = createActivity(new Duration(61));
        final Context context = createContext(new Duration(60));
        final TimeFilter filter = new TimeFilter(10);
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueWhenBothDurationsAreZero() {
        final Activity activity = createActivity(new Duration(0));
        final Context context = createContext(new Duration(0));
        final TimeFilter filter = new TimeFilter(10);
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueWhenActivityDurationIsZero() {
        final Activity activity = createActivity(new Duration(0));
        final Context context = createContext(new Duration(120));
        final TimeFilter filter = new TimeFilter(10);
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsFalseWhenAvailableTimeIsZeroAndActivityHasDuration() {
        final Activity activity = createActivity(new Duration(30));
        final Context context = createContext(new Duration(0));
        final TimeFilter filter = new TimeFilter(10);
        assertFalse(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueWhenBothDurationsAreAtMaximum() {
        final Activity activity = createActivity(new Duration(Duration.MAX_DURATION));
        final Context context = createContext(new Duration(Duration.MAX_DURATION));
        final TimeFilter filter = new TimeFilter(10);
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueWhenActivityIsShortAndAvailableTimeIsMaximum() {
        final Activity activity = createActivity(new Duration(15));
        final Context context = createContext(new Duration(Duration.MAX_DURATION));
        final TimeFilter filter = new TimeFilter(10);
        assertTrue(filter.allows(activity, context));
    }

    private Activity createActivity(final Duration duration) {
        return new Activity("Test Activity", ActivityType.OUTDOOR, new AgeRange(5, 10), duration,
                "Why today", "Description", List.of("Step 1"), List.of("Material 1"), "Safety notes");
    }

    private Context createContext(final Duration availableTime) {
        return new Context(LocationType.CITY, Season.SUMMER, Weather.SUN, 20, new AgeRange(3, 12),
                availableTime, PreferredStyle.OUTDOOR, null);
    }
}
