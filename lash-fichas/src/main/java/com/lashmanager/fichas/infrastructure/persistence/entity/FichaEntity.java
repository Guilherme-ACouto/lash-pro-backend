package com.lashmanager.fichas.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "fichas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaEntity {

  @Id private UUID id;

  @Column(name = "client_id", nullable = false, unique = true)
  private UUID clientId;

  @Column(name = "client_name", nullable = false)
  private String clientName;

  @Column(nullable = false)
  private LocalDate date;

  @Column(name = "skin_type")
  private String skinType;

  @Column(name = "eye_shape")
  private String eyeShape;

  @Column(name = "has_allergies", nullable = false)
  private boolean hasAllergies;

  @Column(name = "allergies_description", columnDefinition = "TEXT")
  private String allergiesDescription;

  @Column(name = "has_medications", nullable = false)
  private boolean hasMedications;

  @Column(name = "medications_description", columnDefinition = "TEXT")
  private String medicationsDescription;

  @Column(name = "has_sensitivities", nullable = false)
  private boolean hasSensitivities;

  @Column(name = "sensitivities_description", columnDefinition = "TEXT")
  private String sensitivitiesDescription;

  @Column(columnDefinition = "TEXT")
  private String observations;

  @Column(nullable = false)
  private boolean active;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
}
