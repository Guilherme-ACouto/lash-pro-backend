package com.lashmanager.appointments.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private UUID id;
    private UUID clientId;
    private UUID serviceId;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    private int durationMinutes;
    private AppointmentStatus status;
    private String notes;
    private UUID financialEntryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
