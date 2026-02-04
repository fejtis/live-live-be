package com.of.ll.domain.model;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.of.ll.domain.exception.DomainValidationException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("MagicNumber")
class ActivityTest {

    @Nested
    class Constructor {

        @Test
        void throwsExceptionWhenTitleIsEmpty() {
            assertThrows(DomainValidationException.class,
                    () -> new Activity("", ActivityType.OUTDOOR, new AgeRange(10, 20), new com.of.ll.domain.model.Duration(5), "Because it's fun", "Description",
                            List.of("Step 1", "Step 2"), List.of("Material 1"), "Safety first"));
        }

        @Test
        void throwsExceptionWhenStepsAreEmpty() {
            assertThrows(DomainValidationException.class,
                    () -> new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 20), new Duration(5), "Because it's fun", "Description",
                            List.of(), List.of("Material 1"), "Safety first"));
        }

        @Test
        void throwsExceptionWhenStepsExceedThreeItems() {
            assertThrows(DomainValidationException.class,
                    () -> new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 20), new Duration(5), "Because it's fun", "Description",
                            List.of("Step 1", "Step 2", "Step 3", "Step 4"), List.of("Material 1"), "Safety first"));
        }

        @Test
        void createsActivityWhenAllFieldsAreValid() {
            final Activity activity = new Activity("Title", ActivityType.OUTDOOR, new AgeRange(10, 15), new Duration(5), "Because it's fun", "Description",
                    List.of("Step 1", "Step 2"), List.of("Material 1"), "Safety first");
            assertNotNull(activity);
        }
    }
}
