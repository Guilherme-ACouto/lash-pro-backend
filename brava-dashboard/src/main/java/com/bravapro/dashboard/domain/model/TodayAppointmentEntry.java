package com.bravapro.dashboard.domain.model;

import java.util.UUID;

public record TodayAppointmentEntry(UUID id, String clientName, String serviceName, String scheduledTime, String status) {}
