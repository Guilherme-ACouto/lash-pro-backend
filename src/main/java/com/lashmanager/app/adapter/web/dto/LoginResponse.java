package com.lashmanager.app.adapter.web.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String name,
        String email,
        String role
) {}
