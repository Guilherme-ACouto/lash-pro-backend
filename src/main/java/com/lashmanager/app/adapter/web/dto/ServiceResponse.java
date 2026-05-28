package com.lashmanager.app.adapter.web.dto;

import java.math.BigDecimal;

public record ServiceResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        int durationMinutes,
        boolean active,
        String createdAt
) {}
