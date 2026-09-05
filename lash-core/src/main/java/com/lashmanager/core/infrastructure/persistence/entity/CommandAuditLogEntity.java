package com.lashmanager.core.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "command_audit_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandAuditLogEntity {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "command_class", nullable = false)
  private String commandClass;

  @Column(name = "payload_json", columnDefinition = "TEXT")
  private String payloadJson;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "executed_at", nullable = false, updatable = false)
  private LocalDateTime executedAt;

  @Column(name = "success", nullable = false)
  private boolean success;

  @PrePersist
  void prePersist() {
    if (executedAt == null) {
      executedAt = LocalDateTime.now();
    }
  }
}
