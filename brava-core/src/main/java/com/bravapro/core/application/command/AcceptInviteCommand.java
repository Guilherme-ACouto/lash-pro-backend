package com.bravapro.core.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bravapro.core.infrastructure.command.AbstractCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Público (sem login): quem identifica o convite é o token. Sem {@code @CommandPermission}. */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AcceptInviteCommand extends AbstractCommand {

    @NotBlank(message = "Link de convite inválido")
    private String token;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 255)
    private String name;

    /** WRITE_ONLY: lida do corpo da requisição, mas nunca serializada no payload da auditoria. */
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
