package com.lashmanager.app.adapter.web.dto;

public record MappingSummaryResponse(
    String clientId,
    String clientName,
    long mappingCount,
    String lastMappingDate
) {}
