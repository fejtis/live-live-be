package com.of.ll.domain.scoring;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

public class AgeFitScoring implements ScoringPolicy {
    @Override
    public int score(final Activity activity, final Context context) {
        return (activity.ageRange().min() - context.ageRange().min() <= 1) && (context.ageRange().max() - activity.ageRange().max() <= 1) ? 2 : 0;
    }
}
