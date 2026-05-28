package com.lashmanager.app.adapter.web.dto;

public record AnamnesePublicResponse(
    String clientName,
    String clientPhone,
    AnamneseResponse anamnese
) {}
