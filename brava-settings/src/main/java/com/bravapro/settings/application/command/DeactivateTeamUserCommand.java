package com.bravapro.settings.application.command;

import com.bravapro.core.infrastructure.command.AbstractCommand;
import com.bravapro.core.infrastructure.command.CommandPermission;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Inativa o acesso (login bloqueado e sessões derrubadas na hora), mantendo o histórico. */
@CommandPermission(admin = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeactivateTeamUserCommand extends AbstractCommand {

    @NotNull
    private UUID id;
}
