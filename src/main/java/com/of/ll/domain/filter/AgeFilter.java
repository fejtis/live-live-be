package com.of.ll.domain.filter;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

/**
 * Filter activities based on age criteria.
 */
public class AgeFilter implements ActivityFilter {
    @Override
    public boolean allows(final Activity activity, final Context context) {
        return context.ageRange().fullyCovers(activity.ageRange());
    }
}
