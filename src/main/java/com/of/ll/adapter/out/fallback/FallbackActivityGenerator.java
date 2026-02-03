package com.of.ll.adapter.out.fallback;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;
import com.of.ll.port.out.ActivityGeneratorPort;

@Component
public class FallbackActivityGenerator implements ActivityGeneratorPort {
    @Override
    public List<Activity> generate(final Context context) {
        final List<Activity> all = FallbackActivities.all();

        if (context.regenerateSeed() == null) {
            return all;
        }

        final int offset = Math.abs(context.regenerateSeed() % all.size());

        return Stream.concat(all.stream().skip(offset), all.stream().limit(offset))
                .toList();
    }
}
