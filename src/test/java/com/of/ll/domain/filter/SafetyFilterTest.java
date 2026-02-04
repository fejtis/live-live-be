package com.of.ll.domain.filter;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("DataFlowIssue")
class SafetyFilterTest {

    @Nested
    class Allows {

        @Test
        void returnsFalseWhenDescriptionContainsForbiddenWord() {
            final String forbiddenWord = firstForbiddenWord();
            final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                    "", "This activity involves " + forbiddenWord, List.of("Step 1", "Step 2"), List.of("Material 1"), "Safety first");
            final SafetyFilter filter = new SafetyFilter();
            assertFalse(filter.allows(activity, null));
        }

        @Test
        void returnsFalseWhenStepsContainForbiddenWord() {
            final String forbiddenWord = firstForbiddenWord();
            final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                    "Description", "Description", List.of("Step 1", "Do " + forbiddenWord), List.of("Material 1"), "Safety first");
            final SafetyFilter filter = new SafetyFilter();
            assertFalse(filter.allows(activity, null));
        }

        @Test
        void returnsTrueWhenNoForbiddenWordsInDescriptionOrSteps() {
            final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                    "Description", "Description", List.of("Step 1", "Step 2"), List.of("Material 1"), "Safety first");
            final SafetyFilter filter = new SafetyFilter();
            assertTrue(filter.allows(activity, null));
        }

        @Test
        void returnsTrueWhenForbiddenWordsAreInDifferentCase() {
            final String forbiddenWord = firstForbiddenWord();
            final Activity activity = new Activity(forbiddenWord.toUpperCase(), ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                    "Description", "Description", List.of("Step 1", "step"), List.of("Material 1"), "Safety first");
            final SafetyFilter filter = new SafetyFilter();
            assertTrue(filter.allows(activity, null));
        }

        @Test
        void returnsFalseWhenMultipleForbiddenWordsArePresent() {
            final String forbiddenWord = firstForbiddenWord();
            final String anotherForbiddenWord = secondForbiddenWord();
            final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                    "Description", "Description", List.of("Step 1", "Step with " + forbiddenWord + " and " + anotherForbiddenWord),
                    List.of("Material 1"), "Safety first");
            final SafetyFilter filter = new SafetyFilter();
            assertFalse(filter.allows(activity, null));
        }
    }

    private static String firstForbiddenWord() {
        return SafetyFilter.FORBIDDEN_WORDS.stream().findFirst().orElseThrow();
    }

    private static String secondForbiddenWord() {
        return SafetyFilter.FORBIDDEN_WORDS.stream().skip(1).findFirst().orElse(firstForbiddenWord());
    }
}
