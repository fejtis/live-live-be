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
class WeatherFilterTest {

    @Test
    void allowsReturnsFalseForOutdoorActivityLongerThan30MinutesInLightRain() {
        final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(31));
        final Context context = createContext(Weather.LIGHT_RAIN);
        final WeatherFilter filter = new WeatherFilter();
        assertFalse(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueForOutdoorActivityExactly30MinutesInLightRain() {
        final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(30));
        final Context context = createContext(Weather.LIGHT_RAIN);
        final WeatherFilter filter = new WeatherFilter();
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueForOutdoorActivityShorterThan30MinutesInLightRain() {
        final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(15));
        final Context context = createContext(Weather.LIGHT_RAIN);
        final WeatherFilter filter = new WeatherFilter();
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueForIndoorActivityLongerThan30MinutesInLightRain() {
        final Activity activity = createActivity(ActivityType.DIY, new Duration(60));
        final Context context = createContext(Weather.LIGHT_RAIN);
        final WeatherFilter filter = new WeatherFilter();
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueForOutdoorActivityLongerThan30MinutesInSun() {
        final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(120));
        final Context context = createContext(Weather.SUN);
        final WeatherFilter filter = new WeatherFilter();
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueForOutdoorActivityLongerThan30MinutesInClouds() {
        final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(120));
        final Context context = createContext(Weather.CLOUDS);
        final WeatherFilter filter = new WeatherFilter();
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueForOutdoorActivityLongerThan30MinutesInHeavyRain() {
        final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(120));
        final Context context = createContext(Weather.HEAVY_RAIN);
        final WeatherFilter filter = new WeatherFilter();
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueForOutdoorActivityLongerThan30MinutesInStorm() {
        final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(120));
        final Context context = createContext(Weather.STORM);
        final WeatherFilter filter = new WeatherFilter();
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueForOutdoorActivityLongerThan30MinutesInSnow() {
        final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(120));
        final Context context = createContext(Weather.SNOW);
        final WeatherFilter filter = new WeatherFilter();
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsTrueForIndoorActivityInAnyWeather() {
        final Activity activity = createActivity(ActivityType.DIY, new Duration(120));
        final Context context = createContext(Weather.STORM);
        final WeatherFilter filter = new WeatherFilter();
        assertTrue(filter.allows(activity, context));
    }

    @Test
    void allowsReturnsFalseForOutdoorActivityAtMaxDurationInLightRain() {
        final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(Duration.MAX_DURATION));
        final Context context = createContext(Weather.LIGHT_RAIN);
        final WeatherFilter filter = new WeatherFilter();
        assertFalse(filter.allows(activity, context));
    }

    private Activity createActivity(final ActivityType activityType, final Duration duration) {
        return new Activity("Test Activity", activityType, new AgeRange(5, 10), duration,
                "Why today", "Description", List.of("Step 1"), List.of("Material 1"), "Safety notes");
    }

    private Context createContext(final Weather weather) {
        return new Context(LocationType.CITY, Season.SUMMER, weather, 20, new AgeRange(3, 12),
                new Duration(120), PreferredStyle.OUTDOOR, null);
    }
}
