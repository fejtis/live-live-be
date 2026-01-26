package com.of.ll.domain.scoring;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

/**
 * Implementation of the ScoringPolicy interface that calculates a score
 * based on the time difference between an activity's duration and the available time in the context.
 */
public class TimeDefaultScoring implements DefaultScoringPolicy {

    private final int idealDiffMinutes;

    public TimeDefaultScoring(final int idealDiffMinutes) {
        this.idealDiffMinutes = idealDiffMinutes;
    }

    /**
     * Calculates the score for the given activity and context.
     * If the activity's duration fits within the available time in the context,
     * considering the maximum time difference, a score of 3 is returned.
     * Otherwise, a score of 0 is returned.
     *
     * @param activity the activity to be scored
     * @param context the context providing the available time
     * @return the calculated score (3 or 0)
     */
    @Override
    public int score(final Activity activity, final Context context) {
        final int activityMinuts = activity.duration().minutes();
        final int availableMinuts = context.availableTime().minutes();
        final int diff = Math.abs(activityMinuts - availableMinuts);

        if (diff <= idealDiffMinutes) {
            return 3;
        }

        return 0;
    }
}
