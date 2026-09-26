package com.bravapro.fichas.domain.model;

import com.bravapro.core.domain.model.DomainEntity;
import com.bravapro.fichas.application.command.UpdateMappingCommand;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Mapping implements DomainEntity {

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

    public void update(UpdateMappingCommand command) {
        this.mappingDate = command.getMappingDate();
        this.mappingType = command.getMappingType();
        this.curvature = command.getCurvature();
        this.humidity = command.getHumidity();
        this.temperature = command.getTemperature();
        this.thickness = command.getThickness();
        this.threadBrand = command.getThreadBrand();
        this.threadFormat = command.getThreadFormat();
        this.adhesive = command.getAdhesive();
        this.lengthsUsed = command.getLengthsUsed();
        this.observations = command.getObservations();
        this.canvasData = command.getCanvasData();
        this.photoBefore = command.getPhotoBefore();
        this.photoAfter = command.getPhotoAfter();
        this.updatedAt = LocalDateTime.now();
    }
}
