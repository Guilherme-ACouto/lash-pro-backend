package com.bravapro.settings.application.command;

import com.bravapro.core.infrastructure.command.AbstractCommand;
import com.bravapro.core.infrastructure.command.CommandPermission;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Derruba todos os tokens do usuário (precisa entrar de novo). */
@CommandPermission(admin = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EndTeamUserSessionsCommand extends AbstractCommand {

    @NotNull
    private UUID id;
}
