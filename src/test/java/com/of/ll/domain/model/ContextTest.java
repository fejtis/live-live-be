package com.of.ll.domain.model;

import org.junit.jupiter.api.Test;

import com.of.ll.domain.exception.DomainValidationException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({ "MagicNumber", "DataFlowIssue" })
class ContextTest {

    @Test
    void constructorThrowsExceptionWhenLocationTypeIsNull() {
        assertThrows(DomainValidationException.class,
                () -> new Context(null, Season.SUMMER, Weather.SUN, 25, new AgeRange(18, 30), new Duration(5), PreferredStyle.MIX));
    }

    @SuppressWarnings("MagicNumber")
    @Test
    void constructorThrowsExceptionWhenSeasonIsNull() {
        assertThrows(DomainValidationException.class,
                () -> new Context(LocationType.CITY, null, Weather.SUN, 25, new AgeRange(18, 30), new Duration(2), PreferredStyle.DIY));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void constructorThrowsExceptionWhenWeatherIsNull() {
        assertThrows(DomainValidationException.class,
                () -> new Context(LocationType.CITY, Season.SUMMER, null, 25, new AgeRange(18, 30), new Duration(5), PreferredStyle.DIY));
    }

    @Test
    void constructorThrowsExceptionWhenTemperatureCIsNull() {
        assertThrows(DomainValidationException.class,
                () -> new Context(LocationType.CITY, Season.SUMMER, Weather.CLOUDS, null, new AgeRange(18, 30), new Duration(5), PreferredStyle.MIX));
    }

    @Test
    void constructorThrowsExceptionWhenAgeRangeIsNull() {
        assertThrows(DomainValidationException.class,
                () -> new Context(LocationType.NATURE, Season.SUMMER, Weather.SNOW, 25, null, new Duration(5), PreferredStyle.MIX));
    }

    @Test
    void constructorThrowsExceptionWhenAvailableTimeIsNull() {
        assertThrows(DomainValidationException.class,
                () -> new Context(LocationType.NATURE, Season.SUMMER, Weather.SNOW, 25, new AgeRange(18, 30), null, PreferredStyle.MIX));
    }

    @Test
    void constructorThrowsExceptionWhenPreferredStyleIsNull() {
        assertThrows(DomainValidationException.class,
                () -> new Context(LocationType.CITY, Season.SUMMER, Weather.HEAVY_RAIN, 25, new AgeRange(18, 30), new Duration(2), null));
    }

    @Test
    void constructorCreatesContextWhenAllFieldsAreValid() {
        final Context context = new Context(LocationType.CITY, Season.SUMMER, Weather.HEAVY_RAIN, 25, new AgeRange(1, 5), new Duration(10),
                PreferredStyle.MIX);
        assertNotNull(context);
    }
}
