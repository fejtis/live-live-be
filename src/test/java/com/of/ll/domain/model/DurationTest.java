package com.of.ll.domain.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.of.ll.domain.exception.DomainValidationException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("MagicNumber")
class DurationTest {

    @Nested
    class Constructor {

        @Test
        void throwsExceptionWhenMinutesAreNegative() {
            assertThrows(DomainValidationException.class, () -> new Duration(-1));
        }

        @Test
        void throwsExceptionWhenMinutesExceedMaxDuration() {
            assertThrows(DomainValidationException.class, () -> new Duration(241));
        }

        @Test
        void createsDurationWhenMinutesAreWithinValidRange() {
            final Duration duration = new Duration(120);
            assertNotNull(duration);
        }
    }

    @Nested
    class FitsWithin {

        @Test
        void returnsTrueWhenDurationFitsWithinOtherWithTolerance() {
            final Duration duration = new Duration(100);
            final Duration other = new Duration(90);
            assertTrue(duration.fitsWithin(other, 15));
        }

        @Test
        void returnsFalseWhenDurationExceedsOtherEvenWithTolerance() {
            final Duration duration = new Duration(100);
            final Duration other = new Duration(80);
            assertFalse(duration.fitsWithin(other, 15));
        }

        @Test
        void returnsTrueWhenDurationEqualsOtherWithTolerance() {
            final Duration duration = new Duration(100);
            final Duration other = new Duration(100);
            assertTrue(duration.fitsWithin(other, 0));
        }

        @Test
        void returnsFalseWhenToleranceIsNegative() {
            final Duration duration = new Duration(100);
            final Duration other = new Duration(90);
            assertFalse(duration.fitsWithin(other, -5));
        }
    }
}
