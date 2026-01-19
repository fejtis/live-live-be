package com.of.ll.domain.model;

import java.util.List;

public record Activity(String title, ActivityType activityType, AgeRange ageRange, Duration duration, String whyToday, String description, List<String> steps, List<String> materials, String safetyNotes) {
}
