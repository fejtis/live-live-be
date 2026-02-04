package com.of.ll.adapter.in.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.of.ll.adapter.in.rest.dto.ActivityResponse;
import com.of.ll.adapter.in.rest.dto.GenerateActivitiesRequest;
import com.of.ll.adapter.in.rest.mapper.ActivityResponseMapper;
import com.of.ll.application.usecase.GenerateDailyActivitiesUseCase;
import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {

    private final GenerateDailyActivitiesUseCase generateDailyActivitiesUseCase;
    private final ContextFactory contextFactory;
    private final ActivityResponseMapper activityResponseMapper;

    public ActivitiesController(final GenerateDailyActivitiesUseCase generateDailyActivitiesUseCase, final ContextFactory contextFactory,
            final ActivityResponseMapper activityResponseMapper) {
        this.generateDailyActivitiesUseCase = generateDailyActivitiesUseCase;
        this.contextFactory = contextFactory;
        this.activityResponseMapper = activityResponseMapper;
    }

    @PostMapping("/generate")
    public List<ActivityResponse> generateActivities(@Valid @RequestBody final GenerateActivitiesRequest generateActivitiesRequest,
            @RequestHeader("X-Client-Id") final String clientId) {
        final Context context = contextFactory.fromRequest(generateActivitiesRequest, clientId);
        final List<Activity> activities = generateDailyActivitiesUseCase.generate(context);

        return activities.stream().map(activityResponseMapper::toResponse).toList();
    }

}
