package com.of.ll.adapter.out.ai.dto;

import java.util.List;

public record AiActivity(String title, String type, String age_range, Integer duration_min, String why_today, String description, List<String> steps,
                         List<String> materials, String safety_notes) {
}
