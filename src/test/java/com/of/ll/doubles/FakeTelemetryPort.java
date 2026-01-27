package com.of.ll.doubles;

import com.of.ll.domain.event.DomainEvent;
import com.of.ll.port.out.TelemetryPort;

public class FakeTelemetryPort implements TelemetryPort {

    public DomainEvent lastEvent;

    @Override
    public void publish(final DomainEvent event) {
        lastEvent = event;
    }

}
