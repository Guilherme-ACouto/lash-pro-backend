package com.lashmanager.app.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.UUID;

@Entity @Table(name = "anamneses") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AnamneseEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "client_id", nullable = false, unique = true)
    private UUID clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private ClientEntity client;

    @Column(name = "guardian_name") private String guardianName;
    @Column(name = "address") private String address;
    @Column(name = "neighborhood") private String neighborhood;
    @Column(name = "city") private String city;
    @Column(name = "state") private String state;
    @Column(name = "birth_date") private LocalDate birthDate;
    @Column(name = "phone") private String phone;
    @Column(name = "cpf") private String cpf;
    @Column(name = "rg") private String rg;
    @Column(name = "had_lash_extensions", nullable = false) private boolean hadLashExtensions;
    @Column(name = "wears_mascara", nullable = false) private boolean wearsMascara;
    @Column(name = "has_allergies", nullable = false) private boolean hasAllergies;
    @Column(name = "has_thyroid_issues", nullable = false) private boolean hasThyroidIssues;
    @Column(name = "sleep_side", nullable = false) private String sleepSide;
    @Column(name = "had_eye_procedure", nullable = false) private boolean hadEyeProcedure;
    @Column(name = "is_pregnant_or_nursing", nullable = false) private boolean isPregnantOrNursing;
    @Column(name = "had_oncological_treatment", nullable = false) private boolean hadOncologicalTreatment;
    @Column(name = "has_skin_disease", nullable = false) private boolean hasSkinDisease;
    @Column(name = "has_health_treatment", nullable = false) private boolean hasHealthTreatment;
    @Column(name = "uses_medication", nullable = false) private boolean usesMedication;
    @Column(name = "term_accepted", nullable = false) private boolean termAccepted;
    @Column(name = "term_accepted_at") private LocalDateTime termAcceptedAt;
    @Column(name = "created_at", nullable = false, updatable = false) private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false) private LocalDateTime updatedAt;

    @PrePersist void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
        if (sleepSide == null) sleepSide = "AMBOS";
    }

    @PreUpdate void preUpdate() { updatedAt = LocalDateTime.now(); }
}
