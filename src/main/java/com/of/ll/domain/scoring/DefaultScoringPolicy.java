package com.of.ll.domain.scoring;

import java.util.List;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

public final class DefaultScoringPolicy implements ScoringPolicy {

    private final List<ScoringPolicy> scoringPolicies;

    public DefaultScoringPolicy(final List<ScoringPolicy> scoringPolicies) {
        this.scoringPolicies = scoringPolicies;
    }

    @Override
    public int score(final Activity activity, final Context context) {
        return scoringPolicies.stream().mapToInt(scoring -> scoring.score(activity, context)).sum();
    }
}
