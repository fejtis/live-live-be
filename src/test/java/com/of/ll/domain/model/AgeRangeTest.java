package com.of.ll.domain.model;

import org.junit.jupiter.api.Test;

import com.of.ll.domain.exception.DomainValidationException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("MagicNumber")
class AgeRangeTest {

    @Test
    void constructorThrowsExceptionWhenMinAgeIsNegative() {
        assertThrows(DomainValidationException.class, () -> new AgeRange(-1, 10));
    }

    @Test
    void constructorThrowsExceptionWhenMaxAgeIsNegative() {
        assertThrows(DomainValidationException.class, () -> new AgeRange(0, -5));
    }

    @Test
    void constructorThrowsExceptionWhenMinAgeExceedsMaxAge() {
        assertThrows(DomainValidationException.class, () -> new AgeRange(10, 5));
    }

    @Test
    void constructorThrowsExceptionWhenMaxAgeExceedsLimit() {
        assertThrows(DomainValidationException.class, () -> new AgeRange(10, 19));
    }

    @Test
    void containsReturnsTrueWhenAgeIsWithinRange() {
        final AgeRange ageRange = new AgeRange(5, 10);
        assertTrue(ageRange.contains(7));
    }

    @Test
    void containsReturnsFalseWhenAgeIsBelowRange() {
        final AgeRange ageRange = new AgeRange(5, 10);
        assertFalse(ageRange.contains(4));
    }

    @Test
    void containsReturnsFalseWhenAgeIsAboveRange() {
        final AgeRange ageRange = new AgeRange(5, 10);
        assertFalse(ageRange.contains(11));
    }

    @Test
    void fullyCoversReturnsTrueWhenOtherRangeIsCompletelyWithin() {
        final AgeRange ageRange = new AgeRange(5, 10);
        final AgeRange other = new AgeRange(6, 9);
        assertTrue(ageRange.fullyCovers(other));
    }

    @Test
    void fullyCoversReturnsFalseWhenOtherRangeExtendsBelow() {
        final AgeRange ageRange = new AgeRange(5, 10);
        final AgeRange other = new AgeRange(4, 9);
        assertFalse(ageRange.fullyCovers(other));
    }

    @Test
    void fullyCoversReturnsFalseWhenOtherRangeExtendsAbove() {
        final AgeRange ageRange = new AgeRange(5, 10);
        final AgeRange other = new AgeRange(6, 11);
        assertFalse(ageRange.fullyCovers(other));
    }

    @Test
    void fullyCoversReturnsFalseWhenOtherRangeIsCompletelyOutside() {
        final AgeRange ageRange = new AgeRange(5, 10);
        final AgeRange other = new AgeRange(11, 15);
        assertFalse(ageRange.fullyCovers(other));
    }
}
