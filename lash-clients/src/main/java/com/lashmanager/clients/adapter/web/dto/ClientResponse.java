package com.lashmanager.clients.adapter.web.dto;

import com.lashmanager.clients.domain.model.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ClientResponse(
        UUID id,
        String name,
        String phone,
        String email,
        LocalDate birthDate,
        String notes,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public static ClientResponse from(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getEmail(),
                client.getBirthDate(),
                client.getNotes(),
                client.isActive(),
                client.getCreatedAt(),
                client.getUpdatedAt());
    }
}
