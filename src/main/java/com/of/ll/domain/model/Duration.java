package com.of.ll.domain.model;

public record Duration(int minutes) {

    public boolean fitsWithin(final Duration other, final int toleranceMinute) {
        return this.minutes <= other.minutes + toleranceMinute;
    }

}
