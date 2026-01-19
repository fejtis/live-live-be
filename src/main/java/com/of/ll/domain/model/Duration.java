package com.of.ll.domain.model;

import com.of.ll.domain.exception.DomainValidationException;

public record Duration(int minutes) {

    public static final int MAX_DURATION = 240;

    public Duration {
        if(minutes < 0) {
            throw new DomainValidationException("Duration minutes must be non-negative.");
        }

        if(minutes > MAX_DURATION) {
            throw new DomainValidationException("Duration minutes cannot exceed 240.");
        }
    }

    public boolean fitsWithin(final Duration other, final int toleranceMinute) {
        return this.minutes <= other.minutes + toleranceMinute;
    }

}
