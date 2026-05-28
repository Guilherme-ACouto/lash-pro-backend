package com.lashmanager.app.adapter.web.dto;

public record ClientResponse(
        String id,
        String name,
        String phone,
        String email,
        String birthDate,
        String notes,
        boolean active,
        String createdAt
) {}
