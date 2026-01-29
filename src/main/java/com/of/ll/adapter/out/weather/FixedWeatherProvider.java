package com.of.ll.adapter.out.weather;

import org.springframework.stereotype.Component;

import com.of.ll.domain.model.LocationType;
import com.of.ll.domain.model.Weather;
import com.of.ll.domain.model.WeatherSnapshot;
import com.of.ll.port.out.WeatherProvider;

@SuppressWarnings("MagicNumber")
@Component
public class FixedWeatherProvider implements WeatherProvider {
    @Override
    public WeatherSnapshot currentWeather(final LocationType locationType) {
        return new WeatherSnapshot(Weather.CLOUDS, 15);
    }
}
