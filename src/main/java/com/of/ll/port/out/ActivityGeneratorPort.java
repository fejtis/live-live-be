package com.of.ll.port.out;

import java.util.List;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

/**
 * <p>Responsibilities:</p>
 * <ul>
 *     <li>Provide activity suggestions</li>
 *     <li>No filtering</li>
 *     <li>No scoring</li>
 * </ul>
 */
@FunctionalInterface
public interface ActivityGeneratorPort {

    /**
     * <p>Can return empty list</p>
     * <p>Does not throw exceptions. Exceptions are handled in adapter layer.</p>
     */
    List<Activity> generate(final Context context);

}
