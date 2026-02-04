package com.of.ll.domain.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.of.ll.domain.exception.DomainValidationException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("MagicNumber")
class AgeRangeTest {

    @Nested
    class Constructor {

        @Test
        void throwsExceptionWhenMinAgeIsNegative() {
            assertThrows(DomainValidationException.class, () -> new AgeRange(-1, 10));
        }

        @Test
        void throwsExceptionWhenMaxAgeIsNegative() {
            assertThrows(DomainValidationException.class, () -> new AgeRange(0, -5));
        }

        @Test
        void throwsExceptionWhenMinAgeExceedsMaxAge() {
            assertThrows(DomainValidationException.class, () -> new AgeRange(10, 5));
        }

        @Test
        void throwsExceptionWhenMaxAgeExceedsLimit() {
            assertThrows(DomainValidationException.class, () -> new AgeRange(10, 19));
        }
    }

    @Nested
    class Contains {

        @Test
        void returnsTrueWhenAgeIsWithinRange() {
            final AgeRange ageRange = new AgeRange(5, 10);
            assertTrue(ageRange.contains(7));
        }

        @Test
        void returnsFalseWhenAgeIsBelowRange() {
            final AgeRange ageRange = new AgeRange(5, 10);
            assertFalse(ageRange.contains(4));
        }

        @Test
        void returnsFalseWhenAgeIsAboveRange() {
            final AgeRange ageRange = new AgeRange(5, 10);
            assertFalse(ageRange.contains(11));
        }
    }

    @Nested
    class FullyCovers {

        @Test
        void returnsTrueWhenOtherRangeIsCompletelyWithin() {
            final AgeRange ageRange = new AgeRange(5, 10);
            final AgeRange other = new AgeRange(6, 9);
            assertTrue(ageRange.fullyCovers(other));
        }

        @Test
        void returnsFalseWhenOtherRangeExtendsBelow() {
            final AgeRange ageRange = new AgeRange(5, 10);
            final AgeRange other = new AgeRange(4, 9);
            assertFalse(ageRange.fullyCovers(other));
        }

        @Test
        void returnsFalseWhenOtherRangeExtendsAbove() {
            final AgeRange ageRange = new AgeRange(5, 10);
            final AgeRange other = new AgeRange(6, 11);
            assertFalse(ageRange.fullyCovers(other));
        }

        @Test
        void returnsFalseWhenOtherRangeIsCompletelyOutside() {
            final AgeRange ageRange = new AgeRange(5, 10);
            final AgeRange other = new AgeRange(11, 15);
            assertFalse(ageRange.fullyCovers(other));
        }
    }
}
