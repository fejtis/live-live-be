package com.of.ll.domain.filter;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

/**
 * Filter principles:
 * <ul
 * <li>never modify {@link com.of.ll.domain.model.Activity}</li>
 * <li>never log</li>
 * <li>do not solve fallback</li>
 * <li>Each filter has one responsibility</li>
 */
@FunctionalInterface
public interface ActivityFilter {

    boolean allows(final Activity activity, final Context context);

}
