package com.of.ll.domain.model;


/**
 * Represents an age range with a minimum and maximum value.
 * This class is implemented as a Java Record, which is a concise way to define immutable data objects.
 *
 * @param min the minimum age in the range (inclusive)
 * @param max the maximum age in the range (inclusive)
 */
public record AgeRange(int min, int max) {

    public boolean contains(final int age) {
        return age >= min && age <= max;
    }

    /**
     * Checks if this age range fully covers another age range.
     * This means that the minimum of this range is less than or equal to the minimum of the other range,
     * and the maximum of this range is greater than or equal to the maximum of the other range.
     *
     * @param other the other AgeRange to compare
     * @return true if this range fully covers the other range, false otherwise
     */
    public boolean fullyCovers(final AgeRange other) {
        return this.min <= other.min && this.max >= other.max;
    }

}
