package com.lashmanager.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {
  private UUID id;
  private String name;
  private String schemaName;
  private boolean active;
  private LocalDateTime createdAt;
}
