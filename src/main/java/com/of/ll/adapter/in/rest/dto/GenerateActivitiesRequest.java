package com.of.ll.adapter.in.rest.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.of.ll.domain.model.LocationType;
import com.of.ll.domain.model.PreferredStyle;

public record GenerateActivitiesRequest(@NotNull LocationType locationType, @Min(1) @Max(240) int availableMinutes,
                                        @Min(0) @Max(18) int minAge, @Min(0) @Max(18) int maxAge, PreferredStyle preferredStyle) {
}
