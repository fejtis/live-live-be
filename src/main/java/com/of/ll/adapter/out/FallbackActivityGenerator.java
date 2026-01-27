package com.of.ll.adapter.out;

import java.util.List;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;
import com.of.ll.port.out.ActivityGeneratorPort;

public class FallbackActivityGenerator implements ActivityGeneratorPort {
    @Override
    public List<Activity> generate(final Context context) {
        return List.of(FallbackActivities.walk());
    }
}
