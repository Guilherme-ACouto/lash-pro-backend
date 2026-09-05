package com.lashmanager.clients.adapter.web.dto;

import com.lashmanager.clients.domain.port.in.CreateClientUseCase;
import java.util.UUID;

public record ClientResponse(
    UUID id,
    String name,
    String phone,
    String email,
    String birthDate,
    String notes,
    boolean active,
    String createdAt) {
  public static ClientResponse from(CreateClientUseCase.ClientResult result) {
    return new ClientResponse(
        result.id(),
        result.name(),
        result.phone(),
        result.email(),
        result.birthDate(),
        result.notes(),
        result.active(),
        result.createdAt());
  }
}
