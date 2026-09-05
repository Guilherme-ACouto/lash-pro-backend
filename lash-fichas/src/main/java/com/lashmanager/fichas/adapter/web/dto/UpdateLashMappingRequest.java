package com.lashmanager.fichas.adapter.web.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record UpdateLashMappingRequest(
    @NotNull LocalDate date,
    String technique,
    String curvature,
    String thickness,
    String length,
    String rightEyeNotes,
    String leftEyeNotes,
    String notes) {}
