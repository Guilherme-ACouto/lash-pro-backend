package com.lashmanager.core.adapter.web.dto;

import java.util.UUID;

public record ActivationResponse(String email, UUID tenantId) {}
