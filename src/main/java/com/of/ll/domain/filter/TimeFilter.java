package com.of.ll.domain.filter;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

/**
 * Filter that allows activities that fit within the available time in the context.
 */
public class TimeFilter implements ActivityFilter {

    private final int toleranceMinutes;

    public TimeFilter(final int toleranceMinutes) {
        this.toleranceMinutes = toleranceMinutes;
    }

    @Override
    public boolean allows(final Activity activity, final Context context) {
        return activity.duration().fitsWithin(context.availableTime(), toleranceMinutes);
    }
}
