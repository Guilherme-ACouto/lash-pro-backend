package com.lashmanager.app.domain.model.dashboard;

public record TodayAppointmentStat(
        String id,
        String clientName,
        String serviceName,
        String scheduledTime,
        String status
) {}
