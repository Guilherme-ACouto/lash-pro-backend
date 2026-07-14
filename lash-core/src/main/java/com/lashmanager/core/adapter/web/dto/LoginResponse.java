package com.lashmanager.core.adapter.web.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String name,
        String email,
        String role
) {}
