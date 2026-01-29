package com.of.ll.port.out;

import com.of.ll.domain.model.LocationType;
import com.of.ll.domain.model.WeatherSnapshot;

@FunctionalInterface
public interface WeatherProvider {

    WeatherSnapshot currentWeather(LocationType locationType);

}
