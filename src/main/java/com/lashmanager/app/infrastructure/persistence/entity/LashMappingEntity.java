package com.lashmanager.app.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.UUID;

@Entity @Table(name = "lash_mappings") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LashMappingEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "client_id", nullable = false) private UUID clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private ClientEntity client;

    @Column(name = "mapping_date", nullable = false) private LocalDate mappingDate;
    @Column(name = "mapping_type") private String mappingType;
    @Column(name = "curvature") private String curvature;
    @Column(name = "humidity") private String humidity;
    @Column(name = "temperature") private String temperature;
    @Column(name = "thickness") private String thickness;
    @Column(name = "thread_brand") private String threadBrand;
    @Column(name = "thread_format") private String threadFormat;
    @Column(name = "adhesive") private String adhesive;
    @Column(name = "lengths_used") private String lengthsUsed;
    @Column(name = "observations", columnDefinition = "TEXT") private String observations;
    @Column(name = "canvas_data", columnDefinition = "TEXT") private String canvasData;
    @Column(name = "photo_before", columnDefinition = "TEXT") private String photoBefore;
    @Column(name = "photo_after", columnDefinition = "TEXT") private String photoAfter;
    @Column(name = "created_at", nullable = false, updatable = false) private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false) private LocalDateTime updatedAt;

    @PrePersist void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
        if (mappingDate == null) mappingDate = LocalDate.now();
    }

    @PreUpdate void preUpdate() { updatedAt = LocalDateTime.now(); }
}
