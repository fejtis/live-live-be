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
import com.of.ll.domain.preferences.UserPreferences;
import com.of.ll.domain.time.SeasonResolver;
import com.of.ll.doubles.FakeActivityHistoryRepository;
import com.of.ll.doubles.FakeUserPreferencesRepository;
import com.of.ll.port.out.WeatherProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
            final FakeUserPreferencesRepository userPreferencesRepository = new FakeUserPreferencesRepository()
                    .withPreferences(new UserPreferences("client-1", PreferredStyle.DIY, Instant.now()));
            final ContextFactory factory = new ContextFactory(weatherProvider, historyRepository, userPreferencesRepository);
            final GenerateActivitiesRequest request = new GenerateActivitiesRequest(LocationType.CITY, 60, 3, 10, PreferredStyle.OUTDOOR, 42);

            final Context context = factory.fromRequest(request, "client-1");

            assertEquals(LocationType.CITY, weatherProvider.lastLocationType);
            assertEquals("client-1", historyRepository.lastClientId);
            assertEquals(2, historyRepository.lastLimit);
            assertEquals("client-1", context.clientId());
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
            final FakeUserPreferencesRepository userPreferencesRepository = new FakeUserPreferencesRepository()
                    .withPreferences(new UserPreferences("client-2", PreferredStyle.OUTDOOR, Instant.now()));
            final ContextFactory factory = new ContextFactory(weatherProvider, historyRepository, userPreferencesRepository);
            final GenerateActivitiesRequest request = new GenerateActivitiesRequest(LocationType.NATURE, 45, 5, 12, PreferredStyle.DIY, null);

            final Context context = factory.fromRequest(request, "client-2");

            assertEquals("client-2", historyRepository.lastClientId);
            assertEquals(2, historyRepository.lastLimit);
            assertTrue(context.excludeTitles().isEmpty());
        }

        @Test
        void usesPreferencesWhenPreferredStyleIsMissing() {
            final StubWeatherProvider weatherProvider = new StubWeatherProvider(new WeatherSnapshot(Weather.SUN, 20));
            final FakeActivityHistoryRepository historyRepository = new FakeActivityHistoryRepository();
            final UserPreferences preferences = new UserPreferences("client-3", PreferredStyle.TRIP, Instant.now());
            final FakeUserPreferencesRepository userPreferencesRepository = new FakeUserPreferencesRepository()
                    .withPreferences(preferences);
            final ContextFactory factory = new ContextFactory(weatherProvider, historyRepository, userPreferencesRepository);
            final GenerateActivitiesRequest request = new GenerateActivitiesRequest(LocationType.CITY, 30, 6, 12, null, null);

            final Context context = factory.fromRequest(request, "client-3");

            assertEquals("client-3", userPreferencesRepository.lastClientId);
            assertEquals(PreferredStyle.TRIP, context.preferredStyle());
        }

        @Test
        void usesMixWhenPreferredStyleMissingAndNoPreferencesFound() {
            final StubWeatherProvider weatherProvider = new StubWeatherProvider(new WeatherSnapshot(Weather.CLOUDS, 14));
            final FakeActivityHistoryRepository historyRepository = new FakeActivityHistoryRepository();
            final FakeUserPreferencesRepository userPreferencesRepository = new FakeUserPreferencesRepository();
            final ContextFactory factory = new ContextFactory(weatherProvider, historyRepository, userPreferencesRepository);
            final GenerateActivitiesRequest request = new GenerateActivitiesRequest(LocationType.VILLAGE, 25, 4, 8, null, null);

            final Context context = factory.fromRequest(request, "client-4");

            assertEquals("client-4", userPreferencesRepository.lastClientId);
            assertEquals(PreferredStyle.MIX, context.preferredStyle());
        }

        @Test
        void doesNotOverridePreferredStyleWhenProvidedInRequest() {
            final StubWeatherProvider weatherProvider = new StubWeatherProvider(new WeatherSnapshot(Weather.SUN, 18));
            final FakeActivityHistoryRepository historyRepository = new FakeActivityHistoryRepository();
            final FakeUserPreferencesRepository userPreferencesRepository = new FakeUserPreferencesRepository()
                    .withPreferences(new UserPreferences("client-5", PreferredStyle.TRIP, Instant.now()));
            final ContextFactory factory = new ContextFactory(weatherProvider, historyRepository, userPreferencesRepository);
            final GenerateActivitiesRequest request = new GenerateActivitiesRequest(LocationType.CITY, 50, 7, 11, PreferredStyle.DIY, null);

            final Context context = factory.fromRequest(request, "client-5");

            assertEquals(PreferredStyle.DIY, context.preferredStyle());
            assertNull(userPreferencesRepository.lastClientId);
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
