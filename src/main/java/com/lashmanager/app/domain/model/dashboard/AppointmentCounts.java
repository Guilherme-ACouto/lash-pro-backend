package com.lashmanager.app.domain.model.dashboard;

public record AppointmentCounts(
        long total,
        long completed,
        long confirmed,
        long scheduled,
        long cancelled
) {}
