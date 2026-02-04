package com.of.ll.adapter.in.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.of.ll.adapter.in.rest.dto.ActivityHistoryResponse;
import com.of.ll.adapter.in.rest.mapper.ActivityHistoryResponseMapper;
import com.of.ll.application.query.GetActivityHistoryQuery;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private static final int MAX_LIMIT = 20;
    private final GetActivityHistoryQuery getActivityHistoryQuery;
    private final ActivityHistoryResponseMapper mapper;

    public HistoryController(final GetActivityHistoryQuery getActivityHistoryQuery, final ActivityHistoryResponseMapper mapper) {
        this.getActivityHistoryQuery = getActivityHistoryQuery;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ActivityHistoryResponse> history(@RequestHeader("X-Client-Id") final String clientId, @RequestParam(defaultValue = "5") final int limit) {
        final int safeLimit = Math.min(limit, MAX_LIMIT); // enforce a maximum limit of 20

        return getActivityHistoryQuery.get(clientId, safeLimit).stream().map(mapper::toResponse).toList();
    }
}
