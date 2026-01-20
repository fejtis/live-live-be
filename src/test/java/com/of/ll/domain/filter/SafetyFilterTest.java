package com.of.ll.domain.filter;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("DataFlowIssue")
class SafetyFilterTest {

    @Test
    void allowsReturnsFalseWhenDescriptionContainsForbiddenWord() {
        final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                "", "This activity involves oheň", List.of("Step 1", "Step 2"), List.of("Material 1"), "Safety first");
        final SafetyFilter filter = new SafetyFilter();
        assertFalse(filter.allows(activity, null));
    }

    @Test
    void allowsReturnsFalseWhenStepsContainForbiddenWord() {
        final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                "Description", "Description", List.of("Step 1", "Zapálit the fire"), List.of("Material 1"), "Safety first");
        final SafetyFilter filter = new SafetyFilter();
        assertFalse(filter.allows(activity, null));
    }

    @Test
    void allowsReturnsTrueWhenNoForbiddenWordsInDescriptionOrSteps() {
        final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                "Description", "Description", List.of("Step 1", "Step 2"), List.of("Material 1"), "Safety first");
        final SafetyFilter filter = new SafetyFilter();
        assertTrue(filter.allows(activity, null));
    }

    @Test
    void allowsReturnsTrueWhenForbiddenWordsAreInDifferentCase() {
        final Activity activity = new Activity("ZAPÁLIT", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                "Description", "Description", List.of("Step 1", "step"), List.of("Material 1"), "Safety first");
        final SafetyFilter filter = new SafetyFilter();
        assertTrue(filter.allows(activity, null));
    }

    @Test
    void allowsReturnsFalseWhenMultipleForbiddenWordsArePresent() {
        final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 18), new Duration(2),
                "Description", "Description", List.of("Step 1", "Step with podpálit and zapálit"), List.of("Material 1"), "Safety first");
        final SafetyFilter filter = new SafetyFilter();
        assertFalse(filter.allows(activity, null));
    }
}
