package com.lashmanager.clients.domain.port.in;

import java.time.LocalDate;
import java.util.UUID;

public interface CreateClientUseCase {

  record CreateClientCommand(
      String name, String phone, String email, LocalDate birthDate, String notes) {}

  record ClientResult(
      UUID id,
      String name,
      String phone,
      String email,
      String birthDate,
      String notes,
      boolean active,
      String createdAt) {}

  ClientResult execute(CreateClientCommand command);
}
