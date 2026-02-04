package com.of.ll.adapter.in.rest;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.of.ll.adapter.in.rest.dto.GenerateActivitiesRequest;
import com.of.ll.domain.history.ActivityHistoryRecord;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.model.Duration;
import com.of.ll.domain.model.LocationType;
import com.of.ll.domain.model.PreferredStyle;
import com.of.ll.domain.model.Weather;
import com.of.ll.domain.model.WeatherSnapshot;
import com.of.ll.domain.time.SeasonResolver;
import com.of.ll.port.out.WeatherProvider;
import com.of.ll.doubles.FakeActivityHistoryRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContextFactoryTest {

    @Nested
    class FromRequest {

        @Test
        void mapsRequestAndHistory() {
            final StubWeatherProvider weatherProvider = new StubWeatherProvider(new WeatherSnapshot(Weather.CLOUDS, 12));
            final List<ActivityHistoryRecord> history = List.of(
                    new ActivityHistoryRecord("client-1", Instant.parse("2026-02-04T00:00:00Z"), List.of("A", "B"), false),
                    new ActivityHistoryRecord("client-1", Instant.parse("2026-02-03T00:00:00Z"), List.of("B", "C"), true)
            );
            final FakeActivityHistoryRepository historyRepository = new FakeActivityHistoryRepository()
                    .withRecent(history);
            final ContextFactory factory = new ContextFactory(weatherProvider, historyRepository);
            final GenerateActivitiesRequest request = new GenerateActivitiesRequest(LocationType.CITY, 60, 3, 10, PreferredStyle.OUTDOOR, 42);

            final Context context = factory.fromRequest(request, "client-1");

            assertEquals(LocationType.CITY, weatherProvider.lastLocationType);
            assertEquals("client-1", historyRepository.lastClientId);
            assertEquals(2, historyRepository.lastLimit);
            assertEquals(request.locationType(), context.locationType());
            assertEquals(SeasonResolver.currentSeason(), context.season());
            assertEquals(Weather.CLOUDS, context.weather());
            assertEquals(12, context.temperatureC());
            assertEquals(new AgeRange(3, 10), context.ageRange());
            assertEquals(new Duration(60), context.availableTime());
            assertEquals(PreferredStyle.OUTDOOR, context.preferredStyle());
            assertEquals(42, context.regenerateSeed());
            assertEquals(List.of("A", "B", "C"), context.excludeTitles());
        }

        @Test
        void usesEmptyExcludeTitlesWhenNoHistory() {
            final StubWeatherProvider weatherProvider = new StubWeatherProvider(new WeatherSnapshot(Weather.SUN, 20));
            final FakeActivityHistoryRepository historyRepository = new FakeActivityHistoryRepository();
            final ContextFactory factory = new ContextFactory(weatherProvider, historyRepository);
            final GenerateActivitiesRequest request = new GenerateActivitiesRequest(LocationType.NATURE, 45, 5, 12, PreferredStyle.DIY, null);

            final Context context = factory.fromRequest(request, "client-2");

            assertEquals("client-2", historyRepository.lastClientId);
            assertEquals(2, historyRepository.lastLimit);
            assertTrue(context.excludeTitles().isEmpty());
        }
    }

    private static final class StubWeatherProvider implements WeatherProvider {
        private final WeatherSnapshot snapshot;
        private LocationType lastLocationType;

        private StubWeatherProvider(final WeatherSnapshot snapshot) {
            this.snapshot = snapshot;
        }

        @Override
        public WeatherSnapshot currentWeather(final LocationType locationType) {
            lastLocationType = locationType;
            return snapshot;
        }
    }

}
