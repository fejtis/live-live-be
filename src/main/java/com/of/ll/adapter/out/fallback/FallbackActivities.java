package com.of.ll.adapter.out.fallback;

import java.util.List;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.ActivityType;
import com.of.ll.domain.model.AgeRange;
import com.of.ll.domain.model.Duration;

final class FallbackActivities {
    private FallbackActivities() {
    }

    @SuppressWarnings("MagicNumber")
    static Activity walk() {
        return new Activity(
                "Procházka v okolí",
                ActivityType.OUTDOOR,
                new AgeRange(3, 15),
                new Duration(30),
                "Krátká procházka je dnes ideální.",
                "Jednoduchá procházka po okolí.",
                List.of(
                        "Vyjděte ven a projděte se po okolí",
                        "Všimněte si tří zajímavých věcí"
                ),
                List.of(),
                "Zůstaňte na známých cestách."
        );
    }
}