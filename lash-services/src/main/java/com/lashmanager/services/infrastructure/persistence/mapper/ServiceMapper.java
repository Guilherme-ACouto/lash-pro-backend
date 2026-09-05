package com.lashmanager.services.infrastructure.persistence.mapper;

import com.lashmanager.services.domain.model.ServiceOffering;
import com.lashmanager.services.infrastructure.persistence.entity.ServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

  public ServiceOffering toDomain(ServiceEntity entity) {
    return ServiceOffering.builder()
        .id(entity.getId())
        .name(entity.getName())
        .description(entity.getDescription())
        .price(entity.getPrice())
        .durationMinutes(entity.getDurationMinutes())
        .active(entity.isActive())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }

  public ServiceEntity toEntity(ServiceOffering domain) {
    return ServiceEntity.builder()
        .id(domain.getId())
        .name(domain.getName())
        .description(domain.getDescription())
        .price(domain.getPrice())
        .durationMinutes(domain.getDurationMinutes())
        .active(domain.isActive())
        .createdAt(domain.getCreatedAt())
        .updatedAt(domain.getUpdatedAt())
        .build();
  }
}
