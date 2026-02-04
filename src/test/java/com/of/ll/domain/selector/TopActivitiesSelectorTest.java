package com.of.ll.domain.selector;

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
import com.of.ll.domain.scoring.DefaultScoringPolicy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("MagicNumber")
class TopActivitiesSelectorTest {

    @Nested
    class SelectTop {

        @Test
        void returnsTopThreeActivitiesSortedByScore() {
            final DefaultScoringPolicy scoringPolicy = mock(DefaultScoringPolicy.class);
            final Context context = createContext();
            final Activity activity1 = createActivity("Activity 1");
            final Activity activity2 = createActivity("Activity 2");
            final Activity activity3 = createActivity("Activity 3");
            final Activity activity4 = createActivity("Activity 4");
            final List<Activity> activities = List.of(activity1, activity2, activity3, activity4);

            when(scoringPolicy.score(activity1, context)).thenReturn(10);
            when(scoringPolicy.score(activity2, context)).thenReturn(30);
            when(scoringPolicy.score(activity3, context)).thenReturn(20);
            when(scoringPolicy.score(activity4, context)).thenReturn(5);

            final TopActivitiesSelector selector = new TopActivitiesSelector(scoringPolicy);
            final List<Activity> result = selector.selectTop(context, activities);

            assertEquals(3, result.size());
            assertEquals(activity2, result.get(0));
            assertEquals(activity3, result.get(1));
            assertEquals(activity1, result.get(2));
        }

        @Test
        void returnsAllActivitiesWhenLessThanThree() {
            final DefaultScoringPolicy scoringPolicy = mock(DefaultScoringPolicy.class);
            final Context context = createContext();
            final Activity activity1 = createActivity("Activity 1");
            final Activity activity2 = createActivity("Activity 2");
            final List<Activity> activities = List.of(activity1, activity2);

            when(scoringPolicy.score(activity1, context)).thenReturn(10);
            when(scoringPolicy.score(activity2, context)).thenReturn(20);

            final TopActivitiesSelector selector = new TopActivitiesSelector(scoringPolicy);
            final List<Activity> result = selector.selectTop(context, activities);

            assertEquals(2, result.size());
            assertEquals(activity2, result.get(0));
            assertEquals(activity1, result.get(1));
        }

        @Test
        void returnsEmptyListWhenNoActivities() {
            final DefaultScoringPolicy scoringPolicy = mock(DefaultScoringPolicy.class);
            final Context context = createContext();
            final List<Activity> activities = List.of();

            final TopActivitiesSelector selector = new TopActivitiesSelector(scoringPolicy);
            final List<Activity> result = selector.selectTop(context, activities);

            assertEquals(0, result.size());
        }

        @Test
        void returnsSingleActivityWhenOnlyOne() {
            final DefaultScoringPolicy scoringPolicy = mock(DefaultScoringPolicy.class);
            final Context context = createContext();
            final Activity activity1 = createActivity("Activity 1");
            final List<Activity> activities = List.of(activity1);

            when(scoringPolicy.score(activity1, context)).thenReturn(10);

            final TopActivitiesSelector selector = new TopActivitiesSelector(scoringPolicy);
            final List<Activity> result = selector.selectTop(context, activities);

            assertEquals(1, result.size());
            assertEquals(activity1, result.getFirst());
        }

        @Test
        void sortsActivitiesByScoreInDescendingOrder() {
            final DefaultScoringPolicy scoringPolicy = mock(DefaultScoringPolicy.class);
            final Context context = createContext();
            final Activity activity1 = createActivity("Activity 1");
            final Activity activity2 = createActivity("Activity 2");
            final Activity activity3 = createActivity("Activity 3");
            final Activity activity4 = createActivity("Activity 4");
            final Activity activity5 = createActivity("Activity 5");
            final List<Activity> activities = List.of(activity1, activity2, activity3, activity4, activity5);

            when(scoringPolicy.score(activity1, context)).thenReturn(5);
            when(scoringPolicy.score(activity2, context)).thenReturn(50);
            when(scoringPolicy.score(activity3, context)).thenReturn(25);
            when(scoringPolicy.score(activity4, context)).thenReturn(40);
            when(scoringPolicy.score(activity5, context)).thenReturn(15);

            final TopActivitiesSelector selector = new TopActivitiesSelector(scoringPolicy);
            final List<Activity> result = selector.selectTop(context, activities);

            assertEquals(3, result.size());
            assertEquals(activity2, result.get(0));
            assertEquals(activity4, result.get(1));
            assertEquals(activity3, result.get(2));
        }

        @Test
        void handlesActivitiesWithSameScore() {
            final DefaultScoringPolicy scoringPolicy = mock(DefaultScoringPolicy.class);
            final Context context = createContext();
            final Activity activity1 = createActivity("Activity 1");
            final Activity activity2 = createActivity("Activity 2");
            final Activity activity3 = createActivity("Activity 3");
            final Activity activity4 = createActivity("Activity 4");
            final List<Activity> activities = List.of(activity1, activity2, activity3, activity4);

            when(scoringPolicy.score(activity1, context)).thenReturn(20);
            when(scoringPolicy.score(activity2, context)).thenReturn(20);
            when(scoringPolicy.score(activity3, context)).thenReturn(20);
            when(scoringPolicy.score(activity4, context)).thenReturn(10);

            final TopActivitiesSelector selector = new TopActivitiesSelector(scoringPolicy);
            final List<Activity> result = selector.selectTop(context, activities);

            assertEquals(3, result.size());
            assertEquals(20, scoringPolicy.score(result.get(0), context));
            assertEquals(20, scoringPolicy.score(result.get(1), context));
            assertEquals(20, scoringPolicy.score(result.get(2), context));
        }

        @Test
        void returnsExactlyThreeActivitiesWhenMoreThanThreeProvided() {
            final DefaultScoringPolicy scoringPolicy = mock(DefaultScoringPolicy.class);
            final Context context = createContext();
            final Activity activity1 = createActivity("Activity 1");
            final Activity activity2 = createActivity("Activity 2");
            final Activity activity3 = createActivity("Activity 3");
            final Activity activity4 = createActivity("Activity 4");
            final Activity activity5 = createActivity("Activity 5");
            final List<Activity> activities = List.of(activity1, activity2, activity3, activity4, activity5);

            when(scoringPolicy.score(activity1, context)).thenReturn(1);
            when(scoringPolicy.score(activity2, context)).thenReturn(2);
            when(scoringPolicy.score(activity3, context)).thenReturn(3);
            when(scoringPolicy.score(activity4, context)).thenReturn(4);
            when(scoringPolicy.score(activity5, context)).thenReturn(5);

            final TopActivitiesSelector selector = new TopActivitiesSelector(scoringPolicy);
            final List<Activity> result = selector.selectTop(context, activities);

            assertEquals(3, result.size());
            assertTrue(result.size() <= 3);
        }
    }

    private Activity createActivity(final String name) {
        return new Activity(name, ActivityType.OUTDOOR, new AgeRange(5, 10), new Duration(30),
                "Why today", "Description", List.of("Step 1"), List.of("Material 1"), "Safety notes");
    }

    private Context createContext() {
        return new Context(UUID.randomUUID().toString(), LocationType.CITY, Season.SUMMER, Weather.SUN, 20,
                new AgeRange(3, 12), new Duration(60), PreferredStyle.OUTDOOR, null, List.of(), false);
    }
}
