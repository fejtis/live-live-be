package com.of.ll.domain.scoring;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Nested;
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
class TimeScoringTest {

    @Nested
    class Score {

        @Test
        void returnsMaximumScoreWhenActivityDurationExactlyMatchesAvailableTime() {
            final Activity activity = createActivity(new Duration(60));
            final Context context = createContext(new Duration(60));
            final TimeScoring scoring = new TimeScoring(10);

            assertEquals(3, scoring.score(activity, context));
        }

        @Test
        void returnsMaximumScoreWhenDifferenceIsWithinIdealLimit() {
            final Activity activity = createActivity(new Duration(65));
            final Context context = createContext(new Duration(60));
            final TimeScoring scoring = new TimeScoring(10);

            assertEquals(3, scoring.score(activity, context));
        }

        @Test
        void returnsMaximumScoreWhenActivityIsShorterWithinIdealLimit() {
            final Activity activity = createActivity(new Duration(55));
            final Context context = createContext(new Duration(60));
            final TimeScoring scoring = new TimeScoring(10);

            assertEquals(3, scoring.score(activity, context));
        }

        @Test
        void returnsZeroWhenDifferenceExceedsIdealLimit() {
            final Activity activity = createActivity(new Duration(80));
            final Context context = createContext(new Duration(60));
            final TimeScoring scoring = new TimeScoring(10);

            assertEquals(0, scoring.score(activity, context));
        }

        @Test
        void returnsZeroWhenActivityIsMuchShorterThanAvailableTime() {
            final Activity activity = createActivity(new Duration(30));
            final Context context = createContext(new Duration(60));
            final TimeScoring scoring = new TimeScoring(10);

            assertEquals(0, scoring.score(activity, context));
        }

        @Test
        void returnsMaximumScoreWhenDifferenceEqualsIdealLimit() {
            final Activity activity = createActivity(new Duration(70));
            final Context context = createContext(new Duration(60));
            final TimeScoring scoring = new TimeScoring(10);

            assertEquals(3, scoring.score(activity, context));
        }

        @Test
        void returnsZeroWhenDifferenceExceedsIdealLimitByOne() {
            final Activity activity = createActivity(new Duration(71));
            final Context context = createContext(new Duration(60));
            final TimeScoring scoring = new TimeScoring(10);

            assertEquals(0, scoring.score(activity, context));
        }

        @Test
        void handlesZeroDurationActivity() {
            final Activity activity = createActivity(new Duration(0));
            final Context context = createContext(new Duration(10));
            final TimeScoring scoring = new TimeScoring(10);

            assertEquals(3, scoring.score(activity, context));
        }

        @Test
        void handlesZeroAvailableTime() {
            final Activity activity = createActivity(new Duration(5));
            final Context context = createContext(new Duration(0));
            final TimeScoring scoring = new TimeScoring(10);

            assertEquals(3, scoring.score(activity, context));
        }

        @Test
        void withSmallIdealDifferenceLimit() {
            final Activity activity = createActivity(new Duration(62));
            final Context context = createContext(new Duration(60));
            final TimeScoring scoring = new TimeScoring(1);

            assertEquals(0, scoring.score(activity, context));
        }

        @Test
        void withLargeIdealDifferenceLimit() {
            final Activity activity = createActivity(new Duration(100));
            final Context context = createContext(new Duration(60));
            final TimeScoring scoring = new TimeScoring(50);

            assertEquals(3, scoring.score(activity, context));
        }

        @Test
        void isSymmetricForActivityShorterAndLongerThanAvailableTime() {
            final Activity shorterActivity = createActivity(new Duration(50));
            final Activity longerActivity = createActivity(new Duration(70));
            final Context context = createContext(new Duration(60));
            final TimeScoring scoring = new TimeScoring(10);

            assertEquals(scoring.score(shorterActivity, context), scoring.score(longerActivity, context));
        }
    }

    private Activity createActivity(final Duration duration) {
        return new Activity("Test Activity", ActivityType.OUTDOOR, new AgeRange(5, 10), duration,
                "Why today", "Description", List.of("Step 1"), List.of("Material 1"), "Safety notes");
    }

    private Context createContext(final Duration availableTime) {
        return new Context(UUID.randomUUID().toString(), LocationType.CITY, Season.SUMMER, Weather.SUN, 20,
                new AgeRange(3, 12), availableTime, PreferredStyle.OUTDOOR, null, List.of());
    }
}
