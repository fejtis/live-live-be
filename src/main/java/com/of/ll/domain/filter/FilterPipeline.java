package com.of.ll.domain.filter;

import java.util.List;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

/**
 * Pipeline to filter activities based on a series of filters.
 */
public class FilterPipeline {

    private final List<ActivityFilter> activityFilters;

    public FilterPipeline() {
        this.activityFilters = List.of(new SafetyFilter(), new AgeFilter(), new TimeFilter(10), new WeatherFilter());
    }

    /**
     * Filters the given list of activities based on the configured filters and context.
     * @param activities the list of activities to filter
     * @param context the context to use for filtering
     * @return the filtered list of activities
     */
    public List<Activity> filterActivities(final List<Activity> activities, final Context context) {
        return activities.stream().filter(activity -> {
            for (final ActivityFilter filter : activityFilters) {
                if (!filter.allows(activity, context)) {
                    return false;
                }
            }

            return true;
        }).toList();
    }
}
