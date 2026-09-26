package com.bravapro.fichas.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "mappings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "mapping_date", nullable = false)
    private LocalDate mappingDate;

    @Column(name = "mapping_type")
    private String mappingType;

    private String curvature;
    private String humidity;
    private String temperature;
    private String thickness;

    @Column(name = "thread_brand")
    private String threadBrand;

    @Column(name = "thread_format")
    private String threadFormat;

    private String adhesive;

    @Column(name = "lengths_used")
    private String lengthsUsed;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(name = "canvas_data", columnDefinition = "TEXT")
    private String canvasData;

    @Column(name = "photo_before", columnDefinition = "TEXT")
    private String photoBefore;

    @Column(name = "photo_after", columnDefinition = "TEXT")
    private String photoAfter;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
