package com.of.ll.domain.filter;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.model.Weather;

public class WeatherFilter implements ActivityFilter {

    /**
     * Disallow outdoor activities longer than 30 minutes in light rain.
     */
    @Override
    public boolean allows(final Activity activity, final Context context) {
        return !Weather.LIGHT_RAIN.equals(context.weather()) || !ActivityType.OUTDOOR.equals(activity.activityType()) || activity.duration().minutes() <= 30;
    }
}
