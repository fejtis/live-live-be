package com.of.ll.domain.scoring;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;
import com.of.ll.domain.model.Context;

public class ActivityTypeScoring implements ScoringPolicy {
    @Override
    public int score(final Activity activity, final Context context) {
        if (ActivityType.OUTDOOR.equals(activity.activityType())) {
            return 2;
        }

        return 0;
    }
}
