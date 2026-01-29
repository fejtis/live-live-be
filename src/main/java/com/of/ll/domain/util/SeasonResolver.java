package com.of.ll.domain.util;

import java.time.LocalDate;
import java.time.Month;

import com.of.ll.domain.model.Season;

public final class SeasonResolver {

    private SeasonResolver() {
    }

    public static Season currentSeason() {
        final LocalDate currentDate = LocalDate.now();
        final Month month = currentDate.getMonth();

        return switch (month) {
            case DECEMBER, JANUARY, FEBRUARY -> Season.WINTER;
            case MARCH, APRIL, MAY -> Season.SPRING;
            case JUNE, JULY, AUGUST -> Season.SUMMER;
            case SEPTEMBER, OCTOBER, NOVEMBER -> Season.AUTUMN;
        };
    }

}
