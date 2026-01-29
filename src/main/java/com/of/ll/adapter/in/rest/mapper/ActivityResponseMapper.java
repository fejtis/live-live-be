package com.of.ll.adapter.in.rest.mapper;

import com.of.ll.adapter.in.rest.dto.ActivityResponse;
import com.of.ll.domain.model.Activity;

public class ActivityResponseMapper {

    public ActivityResponse toResponse(final Activity activity) {
        return new ActivityResponse(activity.title(), activity.description(), activity.steps(), activity.whyToday());
    }

}
