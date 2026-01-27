package com.of.ll.port.out;

import com.of.ll.domain.event.DomainEvent;

/**
 * <p>Responsibilities:</p>
 * <ul>
 *     <li>Accept domain event</li>
 *     <li></li>
 * </ul>
 */
@FunctionalInterface
public interface TelemetryPort {

    /**
     * <ul>
     *  <li>No exceptions to outside</li>
     *  <li>Fire and forget</li>
     * </ul>
     */
    void publish(DomainEvent event);

}
