package com.bravapro.core.application.command;

import com.bravapro.core.infrastructure.command.AbstractCommand;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Equipe da plataforma entrando numa assinatura (suporte). A checagem é de admin de plataforma
 * (domínio do e-mail), não de permissão da assinatura — por isso sem {@code @CommandPermission}.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnterTenantCommand extends AbstractCommand {

    @NotNull
    private UUID tenantId;
}
