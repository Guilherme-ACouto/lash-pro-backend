package com.lashmanager.app.infrastructure.persistence.mapper;

import com.lashmanager.app.domain.model.Service;
import com.lashmanager.app.infrastructure.persistence.entity.ServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public Service toDomain(ServiceEntity entity) {
        if (entity == null) return null;
        return Service.builder()
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

    public ServiceEntity toEntity(Service domain) {
        if (domain == null) return null;
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
