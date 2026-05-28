package com.lashmanager.app.domain.model;

import lombok.*;
import java.time.*;
import java.util.UUID;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class LashMapping {
    private UUID id;
    private UUID clientId;
    private String clientName;
    private LocalDate mappingDate;
    private String mappingType;
    private String curvature;
    private String humidity;
    private String temperature;
    private String thickness;
    private String threadBrand;
    private String threadFormat;
    private String adhesive;
    private String lengthsUsed;
    private String observations;
    private String canvasData;
    private String photoBefore;
    private String photoAfter;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
