package com.of.ll.domain.scoring;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

public class StepsDefaultScoring implements DefaultScoringPolicy {
    @Override
    public int score(final Activity activity, final Context context) {
        return activity.steps().size() == 2 ? 1 : 0;
    }
}
