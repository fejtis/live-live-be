package com.of.ll.domain.model;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.of.ll.domain.exception.DomainValidationException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({ "MagicNumber", "DataFlowIssue" })
class ContextTest {

    @Nested
    class Constructor {

        @Test
        void throwsExceptionWhenLocationTypeIsNull() {
            assertThrows(DomainValidationException.class,
                    () -> new Context(UUID.randomUUID().toString(), null, Season.SUMMER, Weather.SUN, 25, new AgeRange(18, 30), new Duration(5),
                            PreferredStyle.MIX, null, List.of(), false));
        }

        @SuppressWarnings("MagicNumber")
        @Test
        void throwsExceptionWhenSeasonIsNull() {
            assertThrows(DomainValidationException.class,
                    () -> new Context(UUID.randomUUID().toString(), LocationType.CITY, null, Weather.SUN, 25, new AgeRange(18, 30), new Duration(2),
                            PreferredStyle.DIY, null, List.of(), false));
        }

        @SuppressWarnings("DataFlowIssue")
        @Test
        void throwsExceptionWhenWeatherIsNull() {
            assertThrows(DomainValidationException.class,
                    () -> new Context(UUID.randomUUID().toString(), LocationType.CITY, Season.SUMMER, null, 25, new AgeRange(18, 30), new Duration(5),
                            PreferredStyle.DIY, null, List.of(), false));
        }

        @Test
        void throwsExceptionWhenTemperatureCIsNull() {
            assertThrows(DomainValidationException.class,
                    () -> new Context(UUID.randomUUID().toString(), LocationType.CITY, Season.SUMMER, Weather.CLOUDS, null, new AgeRange(18, 30),
                            new Duration(5), PreferredStyle.MIX,
                            null, List.of(), false));
        }

        @Test
        void throwsExceptionWhenAgeRangeIsNull() {
            assertThrows(DomainValidationException.class,
                    () -> new Context(UUID.randomUUID().toString(), LocationType.NATURE, Season.SUMMER, Weather.SNOW, 25, null, new Duration(5),
                            PreferredStyle.MIX, null, List.of(), false));
        }

        @Test
        void throwsExceptionWhenAvailableTimeIsNull() {
            assertThrows(DomainValidationException.class,
                    () -> new Context(UUID.randomUUID().toString(), LocationType.NATURE, Season.SUMMER, Weather.SNOW, 25, new AgeRange(18, 30), null,
                            PreferredStyle.MIX, null, List.of(), false));
        }

        @Test
        void throwsExceptionWhenPreferredStyleIsNull() {
            assertThrows(DomainValidationException.class,
                    () -> new Context(UUID.randomUUID().toString(), LocationType.CITY, Season.SUMMER, Weather.HEAVY_RAIN, 25, new AgeRange(18, 30),
                            new Duration(2), null, null, List.of(), false));
        }

        @Test
        void createsContextWhenAllFieldsAreValid() {
            final Context context = new Context(UUID.randomUUID().toString(), LocationType.CITY, Season.SUMMER, Weather.HEAVY_RAIN, 25, new AgeRange(1, 5),
                    new Duration(10), PreferredStyle.MIX, null, List.of(), false);
            assertNotNull(context);
        }
    }
}
