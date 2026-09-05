package com.lashmanager.fichas.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class LashMapping {

    private UUID id;
    private UUID fichaId;
    private UUID appointmentId;

    private LocalDate date;

    private String technique;
    private String curvature;
    private String thickness;
    private String length;

    private String rightEyeNotes;
    private String leftEyeNotes;
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
