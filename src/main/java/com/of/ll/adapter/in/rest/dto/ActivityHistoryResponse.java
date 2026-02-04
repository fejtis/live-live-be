package com.of.ll.adapter.in.rest.dto;

import java.time.Instant;
import java.util.List;

public record ActivityHistoryResponse(Instant generatedAt, List<String> activities, boolean fallbackUsed) {
}
