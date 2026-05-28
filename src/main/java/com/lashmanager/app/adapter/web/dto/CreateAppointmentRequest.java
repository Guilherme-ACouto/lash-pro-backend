package com.lashmanager.app.adapter.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CreateAppointmentRequest(
        @NotNull(message = "Cliente é obrigatório") UUID clientId,
        @NotNull(message = "Serviço é obrigatório") UUID serviceId,
        @NotNull(message = "Data é obrigatória") LocalDate scheduledDate,
        @NotNull(message = "Horário é obrigatório") LocalTime scheduledTime,
        @NotNull @Min(value = 1, message = "Duração deve ser maior que zero") Integer durationMinutes,
        String notes
) {}
