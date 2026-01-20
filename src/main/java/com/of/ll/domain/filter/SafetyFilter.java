package com.of.ll.domain.filter;

import java.util.Objects;
import java.util.Set;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

/**
 * Responsibilities:
 * <ul>
 *     <li>Decide whether text of {@link com.of.ll.domain.model.Activity} is safe</li>
 *     <li>No transformation</li>
 *     <li>No exceptions</li>
 *     <li>No heuristics like 'probably OK'</li>
 * </ul>
 */
public class SafetyFilter implements ActivityFilter {

    static final Set<String> FORBIDDEN_WORDS = Set.of(
            "oheň",
            "zapálit",
            "podpálit"
    );

    @Override
    public boolean allows(final Activity activity, final Context context) {
        final boolean isDescriptionSafe = FORBIDDEN_WORDS.stream().noneMatch(word -> activity.description().toLowerCase().contains(word));

        final boolean areStepsSafe = activity.steps() == null || activity.steps().stream()
                .filter(Objects::nonNull).map(String::toLowerCase)
                .noneMatch(step -> FORBIDDEN_WORDS.stream().anyMatch(step::contains));

        return isDescriptionSafe && areStepsSafe;
    }
}
