package com.of.ll.adapter.in.rest.dto;

import java.util.List;

public record ActivityResponse(String title, String description, List<String> steps, String whyToday) {
}
