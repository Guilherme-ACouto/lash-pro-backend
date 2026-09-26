package com.bravapro.core.adapter.web.dto;

public record LoginResponse(String accessToken, String refreshToken, String name, String email, boolean admin) {}
