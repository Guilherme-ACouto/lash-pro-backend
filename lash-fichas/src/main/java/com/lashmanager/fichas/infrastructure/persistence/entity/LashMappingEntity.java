package com.lashmanager.fichas.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "lash_mappings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LashMappingEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ficha_id", nullable = false)
    private FichaEntity ficha;

    @Column(name = "appointment_id")
    private UUID appointmentId;

    @Column(nullable = false)
    private LocalDate date;

    private String technique;
    private String curvature;
    private String thickness;
    private String length;

    @Column(name = "right_eye_notes", columnDefinition = "TEXT")
    private String rightEyeNotes;

    @Column(name = "left_eye_notes", columnDefinition = "TEXT")
    private String leftEyeNotes;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
