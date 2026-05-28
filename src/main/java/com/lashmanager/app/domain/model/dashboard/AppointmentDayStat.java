package com.lashmanager.app.domain.model.dashboard;

import java.time.LocalDate;

public record AppointmentDayStat(
        LocalDate date,
        long completed,
        long confirmed,
        long scheduled,
        long cancelled
) {}
