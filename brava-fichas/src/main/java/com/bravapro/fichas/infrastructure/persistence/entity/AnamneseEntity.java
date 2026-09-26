package com.bravapro.fichas.infrastructure.persistence.entity;

import com.bravapro.fichas.domain.model.SleepSide;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "anamneses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnamneseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_id", nullable = false, unique = true)
    private UUID clientId;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "guardian_name")
    private String guardianName;

    private String address;
    private String neighborhood;
    private String city;
    private String state;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String phone;
    private String cpf;
    private String rg;

    @Column(name = "had_lash_extensions", nullable = false)
    private boolean hadLashExtensions;

    @Column(name = "wears_mascara", nullable = false)
    private boolean wearsMascara;

    @Column(name = "has_allergies", nullable = false)
    private boolean hasAllergies;

    @Column(name = "has_thyroid_issues", nullable = false)
    private boolean hasThyroidIssues;

    @Column(name = "sleep_side")
    @Enumerated(EnumType.STRING)
    private SleepSide sleepSide;

    @Column(name = "had_eye_procedure", nullable = false)
    private boolean hadEyeProcedure;

    @Column(name = "is_pregnant_or_nursing", nullable = false)
    private boolean isPregnantOrNursing;

    @Column(name = "had_oncological_treatment", nullable = false)
    private boolean hadOncologicalTreatment;

    @Column(name = "has_skin_disease", nullable = false)
    private boolean hasSkinDisease;

    @Column(name = "has_health_treatment", nullable = false)
    private boolean hasHealthTreatment;

    @Column(name = "uses_medication", nullable = false)
    private boolean usesMedication;

    @Column(name = "term_accepted", nullable = false)
    private boolean termAccepted;

    @Column(name = "term_accepted_at")
    private LocalDateTime termAcceptedAt;

    @Column(name = "link_token")
    private String linkToken;

    @Column(name = "link_token_expiry")
    private LocalDateTime linkTokenExpiry;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
