package com.bravapro.settings.application.command;

import com.bravapro.core.infrastructure.command.AbstractCommand;
import com.bravapro.core.infrastructure.command.CommandPermission;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Envia o e-mail de redefinição de senha pro usuário (a administração nunca vê nem define senha). */
@CommandPermission(admin = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResetTeamUserPasswordCommand extends AbstractCommand {

    @NotNull
    private UUID id;
}
