package com.lashmanager.app.adapter.web.dto;

public record ErrorResponse(
        int status,
        String message,
        String timestamp
) {}
