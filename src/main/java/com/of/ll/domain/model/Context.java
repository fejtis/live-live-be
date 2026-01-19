package com.of.ll.domain.model;

public record Context(LocationType locationType, Season season, Weather weather, int temperatureC, AgeRange ageRange, Duration availableTime, PrefferedStyle prefferedStyle) {


}
