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

@SuppressWarnings("MagicNumber")
class ActivityTypeScoringTest {

    @Test
    void scoreReturnsMaximumScoreForOutdoorActivity() {
        final Activity activity = createActivity(ActivityType.OUTDOOR);
        final Context context = createContext();
        final ActivityTypeScoring scoring = new ActivityTypeScoring();

        assertEquals(2, scoring.score(activity, context));
    }

    @Test
    void scoreReturnsZeroForIndoorActivity() {
        final Activity activity = createActivity(ActivityType.DIY);
        final Context context = createContext();
        final ActivityTypeScoring scoring = new ActivityTypeScoring();

        assertEquals(0, scoring.score(activity, context));
    }

    @Test
    void scoreReturnsZeroForMixActivity() {
        final Activity activity = createActivity(ActivityType.TRIP);
        final Context context = createContext();
        final ActivityTypeScoring scoring = new ActivityTypeScoring();

        assertEquals(0, scoring.score(activity, context));
    }

    @Test
    void scoreReturnsZeroForDiyActivity() {
        final Activity activity = createActivity(ActivityType.DIY);
        final Context context = createContext();
        final ActivityTypeScoring scoring = new ActivityTypeScoring();

        assertEquals(0, scoring.score(activity, context));
    }

    @Test
    void scoreIgnoresContextAndFocusesOnActivityType() {
        final Activity outdoorActivity = createActivity(ActivityType.OUTDOOR);
        final Context anyContext = createContext();
        final ActivityTypeScoring scoring = new ActivityTypeScoring();

        assertEquals(2, scoring.score(outdoorActivity, anyContext));
    }

    private Activity createActivity(final ActivityType activityType) {
        return new Activity("Test Activity", activityType, new AgeRange(5, 10), new Duration(30),
                "Why today", "Description", List.of("Step 1"), List.of("Material 1"), "Safety notes");
    }

    private Context createContext() {
        return new Context(LocationType.CITY, Season.SUMMER, Weather.SUN, 20, new AgeRange(3, 12),
                new Duration(60), PreferredStyle.OUTDOOR, null);
    }
}
