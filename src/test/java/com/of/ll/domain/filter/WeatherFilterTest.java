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
class WeatherFilterTest {

    @Nested
    class Allows {

        @Test
        void returnsFalseForOutdoorActivityLongerThan30MinutesInLightRain() {
            final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(31));
            final Context context = createContext(Weather.LIGHT_RAIN);
            final WeatherFilter filter = new WeatherFilter();
            assertFalse(filter.allows(activity, context));
        }

        @Test
        void returnsTrueForOutdoorActivityExactly30MinutesInLightRain() {
            final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(30));
            final Context context = createContext(Weather.LIGHT_RAIN);
            final WeatherFilter filter = new WeatherFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueForOutdoorActivityShorterThan30MinutesInLightRain() {
            final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(15));
            final Context context = createContext(Weather.LIGHT_RAIN);
            final WeatherFilter filter = new WeatherFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueForIndoorActivityLongerThan30MinutesInLightRain() {
            final Activity activity = createActivity(ActivityType.DIY, new Duration(60));
            final Context context = createContext(Weather.LIGHT_RAIN);
            final WeatherFilter filter = new WeatherFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueForOutdoorActivityLongerThan30MinutesInSun() {
            final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(120));
            final Context context = createContext(Weather.SUN);
            final WeatherFilter filter = new WeatherFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueForOutdoorActivityLongerThan30MinutesInClouds() {
            final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(120));
            final Context context = createContext(Weather.CLOUDS);
            final WeatherFilter filter = new WeatherFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueForOutdoorActivityLongerThan30MinutesInHeavyRain() {
            final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(120));
            final Context context = createContext(Weather.HEAVY_RAIN);
            final WeatherFilter filter = new WeatherFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueForOutdoorActivityLongerThan30MinutesInStorm() {
            final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(120));
            final Context context = createContext(Weather.STORM);
            final WeatherFilter filter = new WeatherFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueForOutdoorActivityLongerThan30MinutesInSnow() {
            final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(120));
            final Context context = createContext(Weather.SNOW);
            final WeatherFilter filter = new WeatherFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsTrueForIndoorActivityInAnyWeather() {
            final Activity activity = createActivity(ActivityType.DIY, new Duration(120));
            final Context context = createContext(Weather.STORM);
            final WeatherFilter filter = new WeatherFilter();
            assertTrue(filter.allows(activity, context));
        }

        @Test
        void returnsFalseForOutdoorActivityAtMaxDurationInLightRain() {
            final Activity activity = createActivity(ActivityType.OUTDOOR, new Duration(Duration.MAX_DURATION));
            final Context context = createContext(Weather.LIGHT_RAIN);
            final WeatherFilter filter = new WeatherFilter();
            assertFalse(filter.allows(activity, context));
        }
    }

    private Activity createActivity(final ActivityType activityType, final Duration duration) {
        return new Activity("Test Activity", activityType, new AgeRange(5, 10), duration,
                "Why today", "Description", List.of("Step 1"), List.of("Material 1"), "Safety notes");
    }

    private Context createContext(final Weather weather) {
        return new Context(UUID.randomUUID().toString(), LocationType.CITY, Season.SUMMER, weather, 20,
                new AgeRange(3, 12), new Duration(120), PreferredStyle.OUTDOOR, null, List.of(), false);
    }
}
