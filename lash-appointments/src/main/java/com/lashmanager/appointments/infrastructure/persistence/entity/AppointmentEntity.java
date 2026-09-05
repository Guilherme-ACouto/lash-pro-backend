package com.lashmanager.appointments.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentEntity {

  @Id
  @Column(updatable = false, nullable = false)
  private UUID id;

  @Column(name = "client_id")
  private UUID clientId;

  @Column(name = "service_id", nullable = false)
  private UUID serviceId;

  @Column(name = "scheduled_date", nullable = false)
  private LocalDate scheduledDate;

  @Column(name = "scheduled_time", nullable = false)
  private LocalTime scheduledTime;

  @Column(name = "duration_minutes", nullable = false)
  private int durationMinutes;

  @Column(nullable = false)
  private String status;

  @Column(columnDefinition = "TEXT")
  private String notes;

  @Column(name = "financial_entry_id")
  private UUID financialEntryId;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
}
