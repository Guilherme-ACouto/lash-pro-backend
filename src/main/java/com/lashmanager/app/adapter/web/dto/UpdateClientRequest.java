package com.lashmanager.app.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateClientRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
        String name,

        @NotBlank(message = "Telefone é obrigatório")
        @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
        String phone,

        @Size(max = 255, message = "E-mail deve ter no máximo 255 caracteres")
        String email,

        @Past(message = "Data de nascimento deve ser uma data passada")
        LocalDate birthDate,

        String notes
) {}
