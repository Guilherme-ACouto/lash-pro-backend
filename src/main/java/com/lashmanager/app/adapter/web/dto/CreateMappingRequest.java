package com.lashmanager.app.adapter.web.dto;
import java.time.LocalDate;

public record CreateMappingRequest(
    LocalDate mappingDate,
    String mappingType,
    String curvature,
    String humidity,
    String temperature,
    String thickness,
    String threadBrand,
    String threadFormat,
    String adhesive,
    String lengthsUsed,
    String observations,
    String canvasData,
    String photoBefore,
    String photoAfter
) {}
