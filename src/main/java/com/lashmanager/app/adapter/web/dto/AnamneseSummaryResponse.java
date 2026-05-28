package com.lashmanager.app.adapter.web.dto;

public record AnamneseSummaryResponse(
    String clientId,
    String clientName,
    boolean hasAnamnese,
    String updatedAt
) {}
