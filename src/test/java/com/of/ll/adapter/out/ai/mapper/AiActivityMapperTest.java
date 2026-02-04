package com.of.ll.adapter.out.ai.mapper;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.of.ll.adapter.out.ai.dto.AiActivity;
import com.of.ll.adapter.out.ai.dto.AiResponse;
import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({ "DataFlowIssue", "MagicNumber" })
class AiActivityMapperTest {

    private AiActivityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AiActivityMapper(new ObjectMapper());
    }

    @Nested
    class Parse {

        @Test
        void returnsResponseForValidJson() throws Exception {
            final String json = """
                    {
                      "activities": [
                        {
                          "title": "Park walk",
                          "type": "outdoor",
                          "age_range": "3-6",
                          "duration_min": 30,
                          "why_today": "Nice weather",
                          "description": "A walk in the park",
                          "steps": ["Step 1"],
                          "materials": ["Water bottle"],
                          "safety_notes": "Watch for traffic"
                        }
                      ]
                    }
                    """;

            final AiResponse response = mapper.parse(json);

            assertEquals(1, response.activities().size());
            assertEquals("Park walk", response.activities().getFirst().title());
            assertEquals("outdoor", response.activities().getFirst().type());
            assertEquals("3-6", response.activities().getFirst().age_range());
            assertEquals(30, response.activities().getFirst().duration_min());
        }

        @Test
        void throwsOnInvalidJson() {
            assertThrows(Exception.class, () -> mapper.parse("not json"));
        }
    }

    @Nested
    class ToDomain {

        @Test
        void mapsValidAiActivity() {
            final AiActivity ai = new AiActivity(
                    "Park walk", "outdoor", "3-6", 30,
                    "Nice weather", "A walk in the park",
                    List.of("Go outside"), List.of("Water bottle"), "Watch for traffic"
            );

            final Activity activity = mapper.toDomain(ai).orElseThrow();

            assertEquals("Park walk", activity.title());
            assertEquals(ActivityType.OUTDOOR, activity.activityType());
            assertEquals(3, activity.ageRange().min());
            assertEquals(6, activity.ageRange().max());
            assertEquals(30, activity.duration().minutes());
            assertEquals("Nice weather", activity.whyToday());
            assertEquals("A walk in the park", activity.description());
            assertEquals(List.of("Go outside"), activity.steps());
            assertEquals(List.of("Water bottle"), activity.materials());
            assertEquals("Watch for traffic", activity.safetyNotes());
        }

        @Test
        void mapsTypeCaseInsensitively() {
            final AiActivity ai = validAiActivity("DIY");
            assertEquals(ActivityType.DIY, mapper.toDomain(ai).orElseThrow().activityType());

            final AiActivity ai2 = validAiActivity("trip");
            assertEquals(ActivityType.TRIP, mapper.toDomain(ai2).orElseThrow().activityType());
        }

        @Test
        void usesEmptyMaterialsWhenNull() {
            final AiActivity ai = new AiActivity(
                    "Title", "outdoor", "3-6", 30,
                    "Why", "Desc", List.of("Step"), null, "Safety"
            );

            final Activity activity = mapper.toDomain(ai).orElseThrow();

            assertEquals(List.of(), activity.materials());
        }

        @Test
        void throwsWhenTitleIsNull() {
            final AiActivity ai = new AiActivity(
                    null, "outdoor", "3-6", 30,
                    "Why", "Desc", List.of("Step"), List.of(), "Safety"
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        @Test
        void throwsWhenTypeIsNull() {
            final AiActivity ai = new AiActivity(
                    "Title", null, "3-6", 30,
                    "Why", "Desc", List.of("Step"), List.of(), "Safety"
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        @Test
        void throwsWhenAgeRangeIsNull() {
            final AiActivity ai = new AiActivity(
                    "Title", "outdoor", null, 30,
                    "Why", "Desc", List.of("Step"), List.of(), "Safety"
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        @Test
        void throwsWhenAgeRangeHasInvalidFormat() {
            final AiActivity ai = new AiActivity(
                    "Title", "outdoor", "3to6", 30,
                    "Why", "Desc", List.of("Step"), List.of(), "Safety"
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        @Test
        void throwsWhenDurationIsNull() {
            final AiActivity ai = new AiActivity(
                    "Title", "outdoor", "3-6", null,
                    "Why", "Desc", List.of("Step"), List.of(), "Safety"
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        @Test
        void throwsWhenWhyTodayIsNull() {
            final AiActivity ai = new AiActivity(
                    "Title", "outdoor", "3-6", 30,
                    null, "Desc", List.of("Step"), List.of(), "Safety"
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        @Test
        void throwsWhenDescriptionIsNull() {
            final AiActivity ai = new AiActivity(
                    "Title", "outdoor", "3-6", 30,
                    "Why", null, List.of("Step"), List.of(), "Safety"
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        @Test
        void throwsWhenSafetyNotesIsNull() {
            final AiActivity ai = new AiActivity(
                    "Title", "outdoor", "3-6", 30,
                    "Why", "Desc", List.of("Step"), List.of(), null
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        @Test
        void throwsWhenStepsIsNull() {
            final AiActivity ai = new AiActivity(
                    "Title", "outdoor", "3-6", 30,
                    "Why", "Desc", null, List.of(), "Safety"
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        @Test
        void throwsWhenStepsIsEmpty() {
            final AiActivity ai = new AiActivity(
                    "Title", "outdoor", "3-6", 30,
                    "Why", "Desc", List.of(), List.of(), "Safety"
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        @Test
        void throwsWhenStepsHasMoreThanFive() {
            final AiActivity ai = new AiActivity(
                    "Title", "outdoor", "3-6", 30,
                    "Why", "Desc", List.of("1", "2", "3", "4", "5", "6"), List.of(), "Safety"
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        @Test
        void throwsWhenTypeIsInvalid() {
            final AiActivity ai = new AiActivity(
                    "Title", "unknown", "3-6", 30,
                    "Why", "Desc", List.of("Step"), List.of(), "Safety"
            );

            assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(ai));
        }

        private AiActivity validAiActivity(final String type) {
            return new AiActivity(
                    "Title", type, "3-6", 30,
                    "Why", "Desc", List.of("Step"), List.of(), "Safety"
            );
        }
    }
}
