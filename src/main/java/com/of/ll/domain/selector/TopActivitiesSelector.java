package com.of.ll.domain.selector;

import java.util.List;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.scoring.DefaultScoringPolicy;

/**
 * Selects the top activities based on scoring policy and context.
 *
 * <p>This selector evaluates a list of activities using a scoring policy and returns
 * the top 3 activities sorted in descending order by their calculated scores.
 * If fewer than 3 activities are provided, all activities are returned sorted by score.
 */
public class TopActivitiesSelector {

    private final DefaultScoringPolicy defaultScoringPolicy;

    public TopActivitiesSelector(final DefaultScoringPolicy defaultScoringPolicy) {
        this.defaultScoringPolicy = defaultScoringPolicy;
    }

    /**
     * Selects and returns the top 3 activities from the provided list based on their scores.
     *
     * <p>Activities are evaluated using the scoring policy against the given context.
     * The returned list is sorted in descending order by score. If fewer than 3 activities
     * are available, all activities are returned sorted by score.
     *
     * @param context the context used for scoring activities
     * @param activities the list of activities to evaluate and select from
     * @return a list containing up to 3 top-scoring activities, sorted in descending order by score
     */
    public List<Activity> selectTop(final Context context, final List<Activity> activities) {
        return activities.stream().sorted((a, b) -> Integer.compare(defaultScoringPolicy.score(b, context), defaultScoringPolicy.score(a, context))).limit(3)
                .toList();
    }
}
