package com.lashmanager.appointments.adapter.web.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CreateAppointmentRequest(
    @NotNull UUID clientId,
    @NotNull UUID serviceId,
    @NotNull LocalDate scheduledDate,
    @NotNull LocalTime scheduledTime,
    int durationMinutes,
    String notes) {}
