package com.lashmanager.clients.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentSummary(String id, LocalDate scheduledDate, LocalTime scheduledTime, String serviceName) {}
