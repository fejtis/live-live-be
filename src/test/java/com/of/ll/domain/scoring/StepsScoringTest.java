package com.of.ll.domain.scoring;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Context;
import com.of.ll.domain.model.Duration;
import com.of.ll.domain.model.LocationType;
import com.of.ll.domain.model.PreferredStyle;
import com.of.ll.domain.model.Season;
import com.of.ll.domain.model.Weather;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StepsScoringTest {

    @Test
    void scoreReturnsOneWhenActivityHasExactlyTwoSteps() {
        final Activity activity = createActivity(List.of("Step 1", "Step 2"));
        final Context context = createContext();
        final StepsScoring scoring = new StepsScoring();

        assertEquals(1, scoring.score(activity, context));
    }

    @Test
    void scoreReturnsZeroWhenActivityHasNoSteps() {
        final Activity activity = createActivity(List.of());
        final Context context = createContext();
        final StepsScoring scoring = new StepsScoring();

        assertEquals(0, scoring.score(activity, context));
    }

    @Test
    void scoreReturnsZeroWhenActivityHasOneStep() {
        final Activity activity = createActivity(List.of("Step 1"));
        final Context context = createContext();
        final StepsScoring scoring = new StepsScoring();

        assertEquals(0, scoring.score(activity, context));
    }

    @Test
    void scoreReturnsZeroWhenActivityHasThreeSteps() {
        final Activity activity = createActivity(List.of("Step 1", "Step 2", "Step 3"));
        final Context context = createContext();
        final StepsScoring scoring = new StepsScoring();

        assertEquals(0, scoring.score(activity, context));
    }

    @Test
    void scoreReturnsZeroWhenActivityHasMoreThanTwoSteps() {
        final Activity activity = createActivity(List.of("Step 1", "Step 2", "Step 3", "Step 4", "Step 5"));
        final Context context = createContext();
        final StepsScoring scoring = new StepsScoring();

        assertEquals(0, scoring.score(activity, context));
    }

    @Test
    void scoreIgnoresContextAndFocusesOnStepCount() {
        final Activity activity = createActivity(List.of("Step 1", "Step 2"));
        final Context anyContext = createContext();
        final StepsScoring scoring = new StepsScoring();

        assertEquals(1, scoring.score(activity, anyContext));
    }

    private Activity createActivity(final List<String> steps) {
        return new Activity("Test Activity", ActivityType.OUTDOOR, new AgeRange(5, 10), new Duration(30),
                "Why today", "Description", steps, List.of("Material 1"), "Safety notes");
    }

    private Context createContext() {
        return new Context(LocationType.CITY, Season.SUMMER, Weather.SUN, 20, new AgeRange(3, 12),
                new Duration(60), PreferredStyle.OUTDOOR);
    }
}
