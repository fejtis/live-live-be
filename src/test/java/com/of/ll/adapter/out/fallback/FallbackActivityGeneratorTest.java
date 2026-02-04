package com.of.ll.adapter.out.fallback;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.model.Duration;
import com.of.ll.domain.model.LocationType;
import com.of.ll.domain.model.PreferredStyle;
import com.of.ll.domain.model.Season;
import com.of.ll.domain.model.Weather;

import static org.junit.jupiter.api.Assertions.assertFalse;

class FallbackActivityGeneratorTest {

    final Context context = new Context(UUID.randomUUID().toString(), LocationType.CITY, Season.SUMMER, Weather.SUN, 20,
            new AgeRange(3, 18), new Duration(60), PreferredStyle.OUTDOOR, null, List.of(), false);

    @Nested
    class Generate {

        @Test
        void alwaysReturnsActivities() {
            final FallbackActivityGenerator generator = new FallbackActivityGenerator();
            final List<Activity> result = generator.generate(context);

            assertFalse(result.isEmpty());
        }
    }

}
