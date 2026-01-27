package com.of.ll.port.out;

import java.time.Instant;

@FunctionalInterface
public interface Clock {
    Instant now();
}
