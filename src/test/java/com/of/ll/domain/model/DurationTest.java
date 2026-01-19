package com.of.ll.domain.model;

import org.junit.jupiter.api.Test;

import com.of.ll.domain.exception.DomainValidationException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("MagicNumber")
class DurationTest {

    @Test
    void constructorThrowsExceptionWhenMinutesAreNegative() {
        assertThrows(DomainValidationException.class, () -> new Duration(-1));
    }

    @Test
    void constructorThrowsExceptionWhenMinutesExceedMaxDuration() {
        assertThrows(DomainValidationException.class, () -> new Duration(241));
    }

    @Test
    void constructorCreatesDurationWhenMinutesAreWithinValidRange() {
        final Duration duration = new Duration(120);
        assertNotNull(duration);
    }

    @Test
    void fitsWithinReturnsTrueWhenDurationFitsWithinOtherWithTolerance() {
        final Duration duration = new Duration(100);
        final Duration other = new Duration(90);
        assertTrue(duration.fitsWithin(other, 15));
    }

    @Test
    void fitsWithinReturnsFalseWhenDurationExceedsOtherEvenWithTolerance() {
        final Duration duration = new Duration(100);
        final Duration other = new Duration(80);
        assertFalse(duration.fitsWithin(other, 15));
    }

    @Test
    void fitsWithinReturnsTrueWhenDurationEqualsOtherWithTolerance() {
        final Duration duration = new Duration(100);
        final Duration other = new Duration(100);
        assertTrue(duration.fitsWithin(other, 0));
    }

    @Test
    void fitsWithinReturnsFalseWhenToleranceIsNegative() {
        final Duration duration = new Duration(100);
        final Duration other = new Duration(90);
        assertFalse(duration.fitsWithin(other, -5));
    }
}
