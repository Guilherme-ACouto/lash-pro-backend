package com.bravapro.core.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotBlank(message = "Link de redefinição inválido") String token,
        @NotBlank(message = "Senha é obrigatória") @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        String password) {}
