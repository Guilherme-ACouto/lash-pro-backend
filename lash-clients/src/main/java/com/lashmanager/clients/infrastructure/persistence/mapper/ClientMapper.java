package com.lashmanager.clients.infrastructure.persistence.mapper;

import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.infrastructure.persistence.entity.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toDomain(ClientEntity entity) {
        return Client.builder()
                .id(entity.getId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .birthDate(entity.getBirthDate())
                .notes(entity.getNotes())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ClientEntity toEntity(Client domain) {
        return ClientEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .phone(domain.getPhone())
                .email(domain.getEmail())
                .birthDate(domain.getBirthDate())
                .notes(domain.getNotes())
                .active(domain.isActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
