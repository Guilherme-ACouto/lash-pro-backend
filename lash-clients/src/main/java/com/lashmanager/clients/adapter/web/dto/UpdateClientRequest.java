package com.lashmanager.clients.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UpdateClientRequest(
    @NotBlank @Size(min = 2, max = 100) String name,
    @NotBlank @Size(min = 10, max = 20) String phone,
    String email,
    LocalDate birthDate,
    @Size(max = 500) String notes) {}
