package com.of.ll.domain.filter;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SafetyFilterTest {

    @Test
    void testReturnsFalseWhenDescriptionContainsForbiddenWord() {
        final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                "", "This activity involves oheň", List.of("Step 1", "Step 2"), List.of("Material 1"), "Safety first");
        final SafetyFilter filter = new SafetyFilter();
        assertFalse(filter.test(activity));
    }

    @Test
    void testReturnsFalseWhenStepsContainForbiddenWord() {
        final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                "Description", "Description", List.of("Step 1", "Zapálit the fire"), List.of("Material 1"), "Safety first");
        final SafetyFilter filter = new SafetyFilter();
        assertFalse(filter.test(activity));
    }

    @Test
    void testReturnsTrueWhenNoForbiddenWordsInDescriptionOrSteps() {
        final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                "Description", "Description", List.of("Step 1", "Step 2"), List.of("Material 1"), "Safety first");
        final SafetyFilter filter = new SafetyFilter();
        assertTrue(filter.test(activity));
    }

    @Test
    void testReturnsTrueWhenForbiddenWordsAreInDifferentCase() {
        final Activity activity = new Activity("ZAPÁLIT", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                "Description", "Description", List.of("Step 1", "step"), List.of("Material 1"), "Safety first");
        final SafetyFilter filter = new SafetyFilter();
        assertTrue(filter.test(activity));
    }

    @Test
    void testReturnsFalseWhenMultipleForbiddenWordsArePresent() {
        final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                "Description", "Description", List.of("Step 1", "Step with podpálit and zapálit"), List.of("Material 1"), "Safety first");
        final SafetyFilter filter = new SafetyFilter();
        assertFalse(filter.test(activity));
    }
}
